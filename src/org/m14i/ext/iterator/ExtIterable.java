package org.m14i.ext.iterator;

import org.m14i.ext.methods.Func1;
import org.m14i.ext.methods.Func2;
import org.m14i.ext.methods.Proc1;
import org.m14i.ext.tuples.Tuple2;
import org.m14i.ext.tuples.Tuple3;
import org.m14i.ext.tuples.Tuple4;

import java.util.*;

public interface ExtIterable<T> extends Iterable<T> {
    /**
     * Checks if all items satisfy the predicate
     *
     * @param predicate
     * @return
     */
    boolean all(Func1<T, Boolean> predicate);

    /**
     * Checks if any item satisfies the predicate
     *
     * @param predicate
     * @return
     */
    boolean any(Func1<T, Boolean> predicate);

    /**
     * Injects the items into the container instance and returns the container
     *
     * @param container
     * @param <X>
     * @return
     */
    <X extends Collection<T>> X as(X container);

    /**
     * Returns the number of items that satisfy the predicate
     *
     * @param predicate
     * @return
     */
    long count(Func1<T, Boolean> predicate);

    /**
     * Remove items from the front of the list
     *
     * @param num
     * @return
     */
    ExtIterable<T> drop(long num);

    /**
     * Execute a procedure for each item
     *
     * @param proc
     */
    void each(Proc1<T> proc);

    /**
     * Remove items that do not satisfy the predicate
     *
     * @param predicate
     * @return
     */
    ExtIterable<T> filter(Func1<T, Boolean> predicate);

    /**
     * Map an item of a type to another type
     *
     * @param map
     * @param <Z>
     * @return
     */
    <Z> ExtIterable<Z> map(Func1<T, Z> map);

    /**
     * Do several mappings at the same time. The result is a tuple containing the results
     * from each function used to map an item
     *
     * @param a
     * @param b
     * @param <A>
     * @param <B>
     * @return
     */
    <A, B> ExtIterable<Tuple2<A, B>> map(Func1<T, A> a, Func1<T, B> b);

    /**
     * Do several mappings at the same time. The result is a tuple containing the results
     * from each function used to map an item
     *
     * @param a
     * @param b
     * @param c
     * @param <A>
     * @param <B>
     * @param <C>
     * @return
     */
    <A, B, C> ExtIterable<Tuple3<A, B, C>> map(Func1<T, A> a, Func1<T, B> b, Func1<T, C> c);

    /**
     * Do several mappings at the same time. The result is a tuple containing the results
     * from each function used to map an item
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @return
     */
    <A, B, C, D> ExtIterable<Tuple4<A, B, C, D>> map(Func1<T, A> a, Func1<T, B> b, Func1<T, C> c, Func1<T, D> d);

    /**
     * Reduces the items to a single object.
     * <p/>
     * Example: finding the sum of all the items
     *
     * @param initial
     * @param reduce
     * @param <Z>
     * @return
     */
    <Z> Z reduce(Z initial, Func2<Z, T, Z> reduce);

    /**
     * @return ExtIterable with sorted items
     */
    ExtIterable<T> sort();

    /**
     * @param comparator
     * @return ExtIterable with sorted items
     */
    ExtIterable<T> sort(final Comparator<T> comparator);

    /**
     * Get the first num items and remove the rest
     *
     * @param num
     * @return
     */
    ExtIterable<T> take(long num);

    /**
     * Call toString() on all items in the collection
     *
     * @return
     */
    ExtIterable<String> toStrings();

    /**
     * Infinitely wraps the list of items.
     * <p/>
     * Example: a, b => a, b, a, b, a, b, ...
     *
     * @return
     */
    ExtIterable<T> wrap();

    /**
     * Places items into a tuple
     *
     * @param items
     * @param <S>
     * @return
     */
    <S> ExtIterable<Tuple2<T, S>> zip(Iterable<S> items);

    /**
     * Places items into a tuple
     *
     * @param a
     * @param b
     * @param <A>
     * @param <B>
     * @return
     */
    <A, B> ExtIterable<Tuple3<T, A, B>> zip(Iterable<A> a, Iterable<B> b);

    /**
     * Places items into a tuple
     *
     * @param a
     * @param b
     * @param c
     * @param <A>
     * @param <B>
     * @param <C>
     * @return
     */
    <A, B, C> ExtIterable<Tuple4<T, A, B, C>> zip(Iterable<A> a, Iterable<B> b, Iterable<C> c);
}
