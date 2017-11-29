package com.linecy.copy.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author by linecy
 */

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.ListViewHolder> {

  private LayoutInflater inflater;
  private Context context;
  private List[] dataArrays;//数据集
  private int[] layoutRes;//布局集
  private int[] variableId;//BR model 集合
  private int size;//整个adapter大小
  private int resSize;//类型大小
  private int[] aloneUpdate;//需要升级的集合
  private Boolean[] orientationArray;//方向集合
  //private Map<Integer, Boolean> layoutResMap;//布局集
  private OnItemClickListener onItemClickListener;
  private HashMap<Integer, ViewDataBinding> dataBindings;

  public BaseAdapter(@NonNull Context context, @NonNull int[] layoutRes, @NonNull int[] variableId,
      Boolean... orientationArray) {
    resSize = layoutRes.length;
    if (orientationArray != null && orientationArray.length > 0) {
      if (resSize != orientationArray.length) {
        throw new RuntimeException("orientation array and layout res set must equals");
      } else {
        this.orientationArray = orientationArray;
      }
    } else {
      this.orientationArray = new Boolean[resSize];
    }

    if (resSize != variableId.length) {
      throw new RuntimeException("layout res set and variable id set must equals");
    }
    this.context = context;
    inflater = LayoutInflater.from(context);
    this.layoutRes = layoutRes;
    this.variableId = variableId;

    dataArrays = new List[resSize];
    aloneUpdate = new int[resSize];
    dataBindings = new HashMap<>();
    size = 0;
  }

  /**
   * 在onCreateViewHolder的时候直接拿到layout，不用遍历
   *
   * @param position adapter中的当前item的位置
   * @return layout id and view type
   */
  @Override public int getItemViewType(int position) {
    for (int i = 0; i < resSize; i++) {
      if (position < dataArrays[i].size()) {
        return layoutRes[i];
      } else {
        position -= dataArrays[i].size();
      }
    }
    return 0;
  }

  @Override public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ViewDataBinding view = DataBindingUtil.inflate(inflater, viewType, parent, false);
    return new ListViewHolder(view, viewType);
  }

  @Override public void onBindViewHolder(ListViewHolder holder, int position) {
    int[] p = getPosition(position);
    if (null == orientationArray[p[0]] || orientationArray[p[0]]) {
      holder.bindData(variableId[p[0]], dataArrays[p[0]]);
    } else {
      holder.bindData(variableId[p[0]], dataArrays[p[0]].get(p[1]));
    }
  }

  @Override public int getItemCount() {
    return size;
  }

  /**
   * 在onBindViewHolder的时候通过数组存储是第几个集合，在该集合中的位置
   *
   * @param position adapter中的当前item的位置
   * @return position[0] 第几个集合 position[1] 在该集合中的位置
   */
  private int[] getPosition(int position) {
    int[] p = new int[2];
    for (int i = 0; i < resSize; i++) {
      if (position < dataArrays[i].size()) {
        p[0] = i;
        p[1] = position;
        return p;
      } else {
        position -= dataArrays[i].size();
      }
    }
    return p;
  }

  void load() {

  }

  class ListViewHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding viewDataBinding;
    private Object object;

    ListViewHolder(ViewDataBinding viewDataBinding, int viewType) {
      super(viewDataBinding.getRoot());
      this.viewDataBinding = viewDataBinding;
      dataBindings.put(viewType, viewDataBinding);
      viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (onItemClickListener != null) {
            onItemClickListener.onItemClick(getLayoutPosition(), object);
          }
        }
      });
    }

    void bindData(int variableId, Object value) {
      viewDataBinding.setVariable(variableId, value);
      viewDataBinding.executePendingBindings();
      this.object = value;
    }
  }

  public interface OnItemClickListener {
    void onItemClick(int position, Object object);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  /**
   * 一直刷新数据的同时并清掉原来的
   *
   * @param list data list
   */
  public void refreshData(List... list) {
    size = 0;
    if (list != null) {
      if (resSize != list.length) {
        throw new RuntimeException("this arrays must equals layout res id arrays");
      } else {
        this.dataArrays = list;
      }
    } else {
      this.dataArrays = new List[] {};
    }
    for (int i = 0; i < this.dataArrays.length; i++) {
      if (null == orientationArray[i] || orientationArray[i]) {
        size++;
      } else {
        size += this.dataArrays[i].size();
      }
    }
    notifyDataSetChanged();
  }

  /**
   * 调用这个方法之前必须保证与设置需要刷新的数据方法传入的顺序一致
   */
  public void refreshForKeepOld(List... list) {

    if (list == null || list.length > resSize) {
      throw new ArrayIndexOutOfBoundsException(
          "this array must no null or no more than need update arrays size");
    }
    int j = 0;
    for (int i = 0; i < resSize; i++) {
      if (aloneUpdate[i] != 0) {
        /*//老数据为null会不会有问题
        for (int k=0;k<list.length;k++){
          if (list[k]!=null&&list[k].size()>0&&list[k].get(0)!=null&&dataArrays[i]!=null&&dataArrays[i].size()>0&&list[k].get(0).equals(dataArrays[i].get(0)))
        }*/
        if (dataArrays[i] == null) {
          dataArrays[i] = new ArrayList();
        }
        if (list[j] != null && list[j].size() > 0) {
          dataArrays[i].addAll(list[j]);
          if (null == orientationArray[i] || orientationArray[i]) {
            size++;
          } else {
            size += list[j].size();
          }
        }

        j++;
      }
    }
    notifyDataSetChanged();
  }

  /**
   * 只针对最后一个item增加
   *
   * @param list data list
   */
  public void refreshForKeepLastOld(List list) {
    if (dataArrays[resSize - 1] == null) {
      dataArrays[resSize - 1] = new ArrayList();
    }
    if (list != null && list.size() > 0) {
      dataArrays[resSize - 1].addAll(list);
      if (null == orientationArray[resSize - 1] || orientationArray[resSize - 1]) {
        size++;
      } else {
        size += list.size();
      }
    } else {
      dataArrays[resSize - 1].clear();
      size -= dataArrays[resSize - 1].size();
    }
    notifyDataSetChanged();
  }

  /**
   * 为需要刷新的数据添加flag
   *
   * @param layoutIdArray layout id array
   */
  public void refreshForKeepOldFlag(int... layoutIdArray) {

    for (int i = 0; i < resSize; i++) {
      for (int layoutId : layoutIdArray) {
        if (layoutRes[i] == layoutId) {
          aloneUpdate[i] = layoutId;//原来这定义一个就，但是后面还要取这个值，所以直接用i的话 就可直接去判断非0取下标
        }
      }
    }
  }

  public HashMap<Integer, ViewDataBinding> getViewDataBinding() {
    return dataBindings;
  }
}
