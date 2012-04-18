package org.m14i.ext.iterator;

import org.m14i.ext.methods.Func1;
import org.m14i.ext.methods.Func2;
import org.m14i.ext.methods.Pred1;
import org.m14i.ext.methods.Proc1;
import org.m14i.ext.tuples.Tuple2;
import org.m14i.ext.tuples.Tuple3;
import org.m14i.ext.tuples.Tuple4;

import java.util.Collection;
import java.util.Comparator;

public interface ExtIterable<T> extends Iterable<T> {
    /**
     * Checks if all items satisfy the predicate
     */
    boolean all(Pred1<T> predicate);

    /**
     * Checks if any item satisfies the predicate
     */
    boolean any(Pred1<T> predicate);

    /**
     * Injects the items into the container instance and returns the container
     */
    <X extends Collection<T>> X into(X container);

    /**
     * Returns the number of items that satisfy the predicate
     */
    long count(Pred1<T> predicate);

    /**
     * Remove items from the front of the list
     */
    ExtIterable<T> drop(long num);

    /**
     * Execute a procedure for each item
     */
    void each(Proc1<T> proc);

    /**
     * Remove items that do not satisfy the predicate
     */
    ExtIterable<T> filter(Pred1<T> predicate);

    /**
     * Map an item of a type to another type
     */
    <Z> ExtIterable<Z> map(Func1<T, Z> map);

    /**
     * Do several mappings at the same time. The result is a tuple containing the results
     * from each function used to map an item
     */
    <A, B> ExtIterable<Tuple2<A, B>> map(Func1<T, A> a, Func1<T, B> b);

    /**
     * Do several mappings at the same time. The result is a tuple containing the results
     * from each function used to map an item
     */
    <A, B, C> ExtIterable<Tuple3<A, B, C>> map(Func1<T, A> a, Func1<T, B> b, Func1<T, C> c);

    /**
     * Do several mappings at the same time. The result is a tuple containing the results
     * from each function used to map an item
     */
    <A, B, C, D> ExtIterable<Tuple4<A, B, C, D>> map(Func1<T, A> a, Func1<T, B> b, Func1<T, C> c, Func1<T, D> d);

    /**
     * Reduces the items to a single object.
     * <p/>
     * Example: finding the sum of all the items
     */
    <Z> Z reduce(Z initial, Func2<Z, T, Z> reduce);

    /**
     * @return ExtIterable with sorted items
     */
    ExtIterable<T> sort();

    /**
     * @return ExtIterable with sorted items
     */
    ExtIterable<T> sort(final Comparator<T> comparator);

    /**
     * Get the first num items and remove the rest
     */
    ExtIterable<T> take(long num);

    /**
     * Call toString() on all items in the collection
     */
    ExtIterable<String> toStrings();

    /**
     * Infinitely wraps the list of items.
     * <p/>
     * Example: a, b => a, b, a, b, a, b, ...
     */
    ExtIterable<T> wrap();

    /**
     * Places items into a tuple
     */
    <S> ExtIterable<Tuple2<T, S>> zip(Iterable<S> items);

    /**
     * Places items into a tuple
     */
    <A, B> ExtIterable<Tuple3<T, A, B>> zip(Iterable<A> a, Iterable<B> b);

    /**
     * Places items into a tuple
     */
    <A, B, C> ExtIterable<Tuple4<T, A, B, C>> zip(Iterable<A> a, Iterable<B> b, Iterable<C> c);

    /**
     * Returns first item that satisfies predicate
     */
    T first(Pred1<T> predicate);

    /**
     * Returns first item
     */
    T head();
}
