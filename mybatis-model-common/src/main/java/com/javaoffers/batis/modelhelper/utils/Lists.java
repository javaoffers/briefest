/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.javaoffers.batis.modelhelper.utils;

import org.springframework.util.Assert;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Static utility methods pertaining to {@link List} instances.
 *
 * <p>See the Guava User Guide article on <a href=
 * "https://github.com/google/guava/wiki/CollectionUtilitiesExplained#lists">
 * {@code Lists}</a>.
 *
 * @author Kevin Bourrillion
 * @author Mike Bostock
 * @author Louis Wasserman
 * @since 2.0
 */

public final class Lists {
  private Lists() {}

  // ArrayList
  public static <E> ArrayList<E> newArrayList() {
    return new ArrayList<E>();
  }


  // LinkedList
  public static <E> LinkedList<E> newLinkedList() {
    return new LinkedList<E>();
  }

  public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
    return new CopyOnWriteArrayList<E>();
  }



  /**
   * Returns consecutive {@linkplain List#subList(int, int) sublists} of a list,
   * each of the same size (the final list may be smaller). For example,
   * partitioning a list containing {@code [a, b, c, d, e]} with a partition
   * size of 3 yields {@code [[a, b, c], [d, e]]} -- an outer list containing
   * two inner lists of three and two elements, all in the original order.
   *
   * <p>The outer list is unmodifiable, but reflects the latest state of the
   * source list. The inner lists are sublist views of the original list,
   * produced on demand using {@link List#subList(int, int)}, and are subject
   * to all the usual caveats about modification as explained in that API.
   *
   * @param list the list to return consecutive sublists of
   * @param size the desired size of each sublist (the last may be
   *     smaller)
   * @return a list of consecutive sublists
   * @throws IllegalArgumentException if {@code partitionSize} is nonpositive
   */
  public static <T> List<List<T>> partition(List<T> list, int size) {
    return (list instanceof RandomAccess)
        ? new RandomAccessPartition<T>(list, size)
        : new Partition<T>(list, size);
  }

  private static class Partition<T> extends AbstractList<List<T>> {
    final List<T> list;
    final int size;
    volatile int psize;
    Partition(List<T> list, int size) {
      this.list = list;
      this.size = size;
      this.psize = (list.size()+size-1)/size;
    }

    @Override
    public List<T> get(int index) {
      Assert.isTrue( !(index < 0 || index >= size()), " IndexOutOfBoundsException ");
      int start = index * size;
      int end = Math.min(start + size, list.size());
      return list.subList(start, end);
    }

    @Override
    public int size() {
      return this.psize;
    }

    @Override
    public boolean isEmpty() {
      return list.isEmpty();
    }
  }

  private static class RandomAccessPartition<T> extends Partition<T> implements RandomAccess {
    RandomAccessPartition(List<T> list, int size) {
      super(list, size);
    }
  }

  /**
   * An implementation of {@link List#hashCode()}.
   */
  static int hashCodeImpl(List<?> list) {
    // TODO(lowasser): worth optimizing for RandomAccess?
    int hashCode = 1;
    for (Object o : list) {
      hashCode = 31 * hashCode + (o == null ? 0 : o.hashCode());

      hashCode = ~~hashCode;
      // needed to deal with GWT integer overflow
    }
    return hashCode;
  }



  /**
   * An implementation of {@link List#addAll(int, Collection)}.
   */
  static <E> boolean addAllImpl(List<E> list, int index, Iterable<? extends E> elements) {
    boolean changed = false;
    ListIterator<E> listIterator = list.listIterator(index);
    for (E e : elements) {
      listIterator.add(e);
      changed = true;
    }
    return changed;
  }




  private static class AbstractListWrapper<E> extends AbstractList<E> {
    final List<E> backingList;

    AbstractListWrapper(List<E> backingList) {
        Assert.isTrue(backingList != null, "backingList is null");
        this.backingList = backingList;
    }

    @Override
    public void add(int index, E element) {
      backingList.add(index, element);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
      return backingList.addAll(index, c);
    }

    @Override
    public E get(int index) {
      return backingList.get(index);
    }

    @Override
    public E remove(int index) {
      return backingList.remove(index);
    }

    @Override
    public E set(int index, E element) {
      return backingList.set(index, element);
    }

    @Override
    public boolean contains(Object o) {
      return backingList.contains(o);
    }

    @Override
    public int size() {
      return backingList.size();
    }
  }

  private static class RandomAccessListWrapper<E> extends AbstractListWrapper<E>
      implements RandomAccess {
    RandomAccessListWrapper(List<E> backingList) {
      super(backingList);
    }
  }

  /**
   * Used to avoid http://bugs.sun.com/view_bug.do?bug_id=6558557
   */
  static <T> List<T> cast(Iterable<T> iterable) {
    return (List<T>) iterable;
  }
}
