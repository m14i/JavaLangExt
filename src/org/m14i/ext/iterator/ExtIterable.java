package org.m14i.ext.iterator;

import static org.m14i.ext.Ext.from;

import org.m14i.ext.methods.Fn1;
import org.m14i.ext.methods.Fn2;
import org.m14i.ext.methods.Pred;
import org.m14i.ext.methods.Proc;
import org.m14i.ext.tuples.Tuple2;
import org.m14i.ext.tuples.Tuple3;
import org.m14i.ext.tuples.Tuple4;
import org.m14i.ext.utils.Functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
        Pred<T> wrapper = new Pred<T>() {
            @Override
            public Boolean apply(T x) {
                return !predicate.apply(x);
            }
        };

        return !any(wrapper);
    }

    /**
     * Checks if any item satisfies the predicate
     */
    public boolean any(final Pred<T> predicate) {
        while (iterator.hasNext())
            if (predicate.apply(iterator.next()))
                return true;

        return false;
    }

    /**
     * Returns the number of items that satisfy the predicate
     */
    public long count(final Pred<T> predicate) {
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
        if (iterator.hasNext())
            return iterator.next();

        return null;
    }

    /**
     * Remove duplicates
     */
    public ExtIterable<T> distinct() {
        final Set<T> set = new HashSet<T>();
        return filter(new Pred<T>() {
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
     * Group items according to emitted key
     */
    public <K> Map<K, List<T>> group(final Fn1<T, K> emitKey) {
        return reduce(new HashMap<K, List<T>>(), new Fn2<Map<K, List<T>>, T, Map<K, List<T>>>() {
            @Override
            public Map<K, List<T>> apply(Map<K, List<T>> acc, T x) {
                K key = emitKey.apply(x);
                List<T> list = acc.get(key);

                if (list == null) {
                    list = new ArrayList<T>();
                    acc.put(key, list);
                }

                list.add(x);

                return acc;
            }
        });
    }

    /**
     * Convert items to string and join using separator
     */
    public String join(String separator) {
        return toStrings().reduce("", Functions.join(separator));
    }

    /**
     * Reduces the items to a single object.
     */
    public <Z> Z reduce(final Z init, final Fn2<Z, T, Z> reduce) {
        Z acc = init;
        while (iterator.hasNext())
            acc = reduce.apply(acc, iterator.next());

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
     * Execute a procedure for each item
     */
    public void each(final Proc<T> proc) {
        while (iterator.hasNext())
            proc.apply(iterator.next());
    }

    /**
     * Map an item of a type to another type
     */
    public <Z> ExtIterable<Z> map(final Fn1<T, Z> map) {
        return from(new ImmutableIterator<Z>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Z next() {
                return map.apply(iterator.next());
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
     * @return ExtIterable with sorted items
     */
    @SuppressWarnings("unchecked")
    public ExtIterable<T> sort() {
        List list = into(new ArrayList());
        Collections.sort(list);
        return from(list.iterator());
    }

    /**
     * @return ExtIterable with sorted items
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
        while (iterator.hasNext())
            container.add(iterator.next());

        return container;
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
}
