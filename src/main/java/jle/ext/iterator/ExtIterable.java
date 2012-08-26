package jle.ext.iterator;

import static jle.ext.Ext.from;

import jle.ext.methods.Fn1;
import jle.ext.methods.Fn2;
import jle.ext.methods.Pred;
import jle.ext.methods.Proc;
import jle.ext.tuples.Tuple2;
import jle.ext.tuples.Tuple3;
import jle.ext.tuples.Tuple4;
import jle.ext.utils.Functions;
import jle.ext.utils.Predicates;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExtIterable<T> implements Iterable<T> {

    private final Iterator<T> iterator;

    public ExtIterable(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Iterator<T> iterator() {
        return this.iterator;
    }

    /**
     * Checks if all items satisfy the predicate
     */
    public boolean all(final Pred<T> predicate) {
        return !any(Predicates.invert(predicate));
    }

    /**
     * Checks if any item satisfies the predicate
     */
    public boolean any(final Pred<T> predicate) {
        return null != first(predicate);
    }

    /**
     * Returns the number of items that satisfy the predicate
     */
    public Long count(final Pred<T> predicate) {
        return reduce(0L, new Fn2<Long, T, Long>() {
            @Override
            public Long apply(Long acc, T x) {
                return acc + (predicate.apply(x) ? 1L : 0L);
            }
        });
    }

    /**
     * Returns first item
     */
    public T head() {
        return iterator.hasNext() ? iterator.next() : null;
    }

    /**
     * Remove duplicates
     */
    public ExtIterable<T> distinct() {
        return filter(new Pred<T>() {
            Set<T> set = new LinkedHashSet<T>();

            @Override
            public Boolean apply(T x) {
                if (set.contains(x))
                    return false;

                set.add(x);
                return true;
            }
        });
    }

    /**
     * Enumerate items in list
     * Example:
     * "a", "b", "c" -> (0, "a"), (1, "b"), (2, "c")
     */
    public ExtIterable<Tuple2<Long, T>> enumerate() {
        return map(new Fn1<T, Tuple2<Long, T>>() {
            long count = 0;

            @Override
            public Tuple2<Long, T> apply(T x) {
                return new Tuple2<Long, T>(count++, x);
            }
        });
    }

    /**
     * Returns first item that satisfies predicate
     */
    public T first(final Pred<T> predicate) {
        return filter(predicate).head();
    }

    /**
     * Group items according to emitted key and return ExtIterable
     */
    public <K> ExtIterable<Tuple2<K, List<T>>> group(final Fn1<T, K> emitKey) {
        Map<K, Tuple2<K, List<T>>> lookup = new LinkedHashMap<K, Tuple2<K, List<T>>>();

        while (iterator.hasNext()) {
            T item = iterator.next();
            K key = emitKey.apply(item);
            Tuple2<K, List<T>> tuple = lookup.get(key);

            if (tuple == null) {
                tuple = new Tuple2<K, List<T>>(key, new ArrayList<T>());
                lookup.put(key, tuple);
            }

            tuple._2().add(item);
        }

        return from(lookup.values());
    }

    /**
     * Group items according to emitted key and return ExtIterable
     */
    public <K> ExtIterable<Tuple2<K, Set<T>>> groupDistinct(final Fn1<T, K> emitKey) {
        Map<K, Tuple2<K, Set<T>>> lookup = new LinkedHashMap<K, Tuple2<K, Set<T>>>();

        while (iterator.hasNext()) {
            T item = iterator.next();
            K key = emitKey.apply(item);
            Tuple2<K, Set<T>> tuple = lookup.get(key);

            if (tuple == null) {
                tuple = new Tuple2<K, Set<T>>(key, new HashSet<T>());
                lookup.put(key, tuple);
            }

            tuple._2().add(item);
        }

        return from(lookup.values());
    }

    /**
     * Convert items to string and join using separator
     */
    public String join(String separator) {
        return toStrings().reduce(new StringBuffer(), Functions.join(separator)).toString();
    }

    /**
     * Reduces the items to a single object.
     */
    public <Z> Z reduce(final Z init, final Fn2<Z, T, Z> reducer) {
        Z acc = init;
        while (iterator.hasNext())
            acc = reducer.apply(acc, iterator.next());

        return acc;
    }

    /**
     * Drop n items from the front of the list
     */
    public ExtIterable<T> drop(final long n) {
        return filter(new Pred<T>() {
            long count = 0;

            @Override
            public Boolean apply(T _) {
                return count++ >= n;
            }
        });
    }

    /**
     * take n items from the front of the list
     */
    public ExtIterable<T> take(final long num) {
        return from(new ImmutableIterator<T>() {
            long count = 0;

            @Override
            public boolean hasNext() {
                return iterator.hasNext() && count < num;
            }

            @Override
            public T next() {
                count++;
                return iterator.next();
            }
        });
    }

    /**
     * Remove items that do not satisfy the predicate
     */
    public ExtIterable<T> filter(final Pred<T> predicate) {
        return from(new ImmutableIterator<T>() {
            T xn = findNext();

            T findNext() {
                while (iterator.hasNext()) {
                    T x = iterator.next();
                    if (predicate.apply(x))
                        return x;
                }
                return null;
            }

            @Override
            public boolean hasNext() {
                return xn != null;
            }

            @Override
            public T next() {
                T current = xn;
                xn = findNext();
                return current;
            }
        });
    }

    /**
     * Remove items that satisfy the predicate
     */
    public ExtIterable<T> reject(final Pred<T> predicate) {
        return filter(Predicates.invert(predicate));
    }

    /**
     * Execute a procedure for each item
     */
    public void each(final Proc<T> proc) {
        while (iterator.hasNext())
            proc.apply(iterator.next());
    }

    /**
     * Map an item of a type to another type
     */
    public <Z> ExtIterable<Z> map(final Fn1<T, Z> fn) {
        return from(new ImmutableIterator<Z>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Z next() {
                return fn.apply(iterator.next());
            }
        });
    }

    /**
     * Map an item of a type to another type
     */
    public <A, B> ExtIterable<Tuple2<A, B>> map(final Fn1<T, A> a, final Fn1<T, B> b) {
        return from(new ImmutableIterator<Tuple2<A, B>>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Tuple2<A, B> next() {
                T x = iterator.next();
                return new Tuple2<A, B>(a.apply(x), b.apply(x));
            }
        });
    }

    /**
     * Map an item of a type to another type
     */
    public <A, B, C> ExtIterable<Tuple3<A, B, C>> map(final Fn1<T, A> a,
                                                      final Fn1<T, B> b,
                                                      final Fn1<T, C> c) {
        return from(new ImmutableIterator<Tuple3<A, B, C>>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Tuple3<A, B, C> next() {
                T x = iterator.next();
                return new Tuple3<A, B, C>(a.apply(x), b.apply(x), c.apply(x));
            }
        });
    }

    /**
     * Map an item of a type to another type
     */
    public <A, B, C, D> ExtIterable<Tuple4<A, B, C, D>> map(final Fn1<T, A> a,
                                                            final Fn1<T, B> b,
                                                            final Fn1<T, C> c,
                                                            final Fn1<T, D> d) {
        return from(new ImmutableIterator<Tuple4<A, B, C, D>>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Tuple4<A, B, C, D> next() {
                T x = iterator.next();
                return new Tuple4<A, B, C, D>(a.apply(x), b.apply(x), c.apply(x), d.apply(x));
            }
        });
    }

    /**
     * ExtIterable with sorted items
     */
    @SuppressWarnings("unchecked")
    public ExtIterable<T> sort() {
        List list = into(new ArrayList());
        Collections.sort(list);
        return from(list.iterator());
    }

    /**
     * ExtIterable with sorted items
     */
    public ExtIterable<T> sort(final Comparator<T> comparator) {
        List<T> list = into(new ArrayList<T>());
        Collections.sort(list, comparator);
        return from(list.iterator());
    }

    /**
     * Injects the items into the container instance and returns the container
     */
    public <X extends Collection<T>> X into(X container) {
        return reduce(container, new Fn2<X, T, X>() {
            @Override
            public X apply(X xs, T x) {
                xs.add(x);
                return xs;
            }
        });
    }

    /**
     * Injects the tuple2 items into the map container instance and returns the container
     */
    public <X extends Map> X into(X container) {
        return reduce(container, new Fn2<X, T, X>() {
            @SuppressWarnings("unchecked")
            @Override
            public X apply(X xs, T x) {
                Tuple2 t = (Tuple2) x;
                xs.put(t._1(), t._2());
                return xs;
            }
        });
    }

    /**
     * Call toString() on all items in the collection
     */
    public ExtIterable<String> toStrings() {
        return map(new Fn1<T, String>() {
            @Override
            public String apply(T x) {
                return x.toString();
            }
        });
    }

    /**
     * Infinitely wraps the list of items.
     * <p/>
     * Example: a, b => a, b, a, b, a, b, ...
     */
    public ExtIterable<T> wrap() {
        return from(new ImmutableIterator<T>() {
            List<T> coll = new LinkedList<T>();
            Iterator<T> it = iterator;
            boolean isLoaded = false;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                T x;
                if (it.hasNext()) {
                    x = it.next();
                    if (!isLoaded)
                        coll.add(x);
                } else {
                    isLoaded = true;
                    it = coll.iterator();
                    x = it.next();
                }
                return x;
            }
        });
    }

    /**
     * Places items into a tuple
     */
    public <S> ExtIterable<Tuple2<T, S>> zip(final Iterable<S> xs) {
        final Iterator<S> it = xs.iterator();
        return from(new ImmutableIterator<Tuple2<T, S>>() {
            @Override
            public boolean hasNext() {
                return it.hasNext() && iterator.hasNext();
            }

            @Override
            public Tuple2<T, S> next() {
                return new Tuple2<T, S>(iterator.next(), it.next());
            }
        });
    }

    /**
     * Places items into a tuple
     */
    public <A, B> ExtIterable<Tuple3<T, A, B>> zip(final Iterable<A> a, final Iterable<B> b) {
        final Iterator<A> a_ = a.iterator();
        final Iterator<B> b_ = b.iterator();
        return from(new ImmutableIterator<Tuple3<T, A, B>>() {
            @Override
            public boolean hasNext() {
                return a_.hasNext() && b_.hasNext() && iterator.hasNext();
            }

            @Override
            public Tuple3<T, A, B> next() {
                return new Tuple3<T, A, B>(iterator.next(), a_.next(), b_.next());
            }
        });
    }

    /**
     * Places items into a tuple
     */
    public <A, B, C> ExtIterable<Tuple4<T, A, B, C>> zip(final Iterable<A> a,
                                                         final Iterable<B> b,
                                                         final Iterable<C> c) {
        final Iterator<A> a_ = a.iterator();
        final Iterator<B> b_ = b.iterator();
        final Iterator<C> c_ = c.iterator();
        return from(new ImmutableIterator<Tuple4<T, A, B, C>>() {
            @Override
            public boolean hasNext() {
                return a_.hasNext() && b_.hasNext() && c_.hasNext() && iterator.hasNext();
            }

            @Override
            public Tuple4<T, A, B, C> next() {
                return new Tuple4<T, A, B, C>(iterator.next(), a_.next(), b_.next(), c_.next());
            }
        });
    }

    /**
     * Return list implementation
     */
    public List<T> toList() {
        return into(new ArrayList<T>());
    }

    /**
     * Return map implementation
     */
    public <K, V> Map<K, V> toMap() {
        return into(new HashMap<K, V>());
    }

    /**
     * Return set implementation
     */
    public Set<T> toSet() {
        return into(new HashSet<T>());
    }

    /**
     * Removes all instances of item from list
     */
    public ExtIterable<T> remove(final T item) {
        return filter(Predicates.isNot(item));
    }

    /**
     * Count how many items have the same emitted key
     */
    public <K> ExtIterable<Tuple2<K, Integer>> frequency(Fn1<T, K> emitKey) {
        return group(emitKey).map(new Fn1<Tuple2<K, List<T>>, Tuple2<K, Integer>>() {
            @Override
            public Tuple2<K, Integer> apply(Tuple2<K, List<T>> tuple) {
                return new Tuple2<K, Integer>(tuple._1(), tuple._2().size());
            }
        });
    }
}