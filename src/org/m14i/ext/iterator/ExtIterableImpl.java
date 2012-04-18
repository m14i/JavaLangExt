package org.m14i.ext.iterator;

import org.m14i.ext.methods.Func1;
import org.m14i.ext.methods.Func2;
import org.m14i.ext.methods.Pred1;
import org.m14i.ext.methods.Proc1;
import org.m14i.ext.tuples.Tuple2;
import org.m14i.ext.tuples.Tuple3;
import org.m14i.ext.tuples.Tuple4;

import java.util.*;

public class ExtIterableImpl<T> implements ExtIterable<T> {
    private Iterator<T> iterator;

    public ExtIterableImpl(Iterable<T> iterable) {
        this(iterable.iterator());
    }

    public ExtIterableImpl(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean all(final Pred1<T> predicate) {
        Pred1<T> wrapper = new Pred1<T>() {
            @Override
            public Boolean apply(T x) {
                return !predicate.apply(x);
            }
        };

        return !any(wrapper);
    }

    @Override
    public boolean any(final Pred1<T> predicate) {
        while (iterator.hasNext())
            if (predicate.apply(iterator.next()))
                return true;

        return false;
    }

    @Override
    public long count(final Pred1<T> predicate) {
        return reduce(0L, new Func2<Long, T, Long>() {
            @Override
            public Long apply(Long acc, T x) {
                return acc + (predicate.apply(x) ? 1L : 0L);
            }
        });
    }

    @Override
    public T head() {
        if (iterator.hasNext())
            return iterator.next();

        return null;
    }

    @Override
    public T first(final Pred1<T> predicate) {
        return filter(predicate).head();
    }

    @Override
    public <Z> Z reduce(final Z initial, final Func2<Z, T, Z> reduce) {
        Z acc = initial;
        while (iterator.hasNext())
            acc = reduce.apply(acc, iterator.next());

        return acc;
    }

    @Override
    public ExtIterable<T> drop(final long num) {
        return filter(new Pred1<T>() {
            long count = 0;

            @Override
            public Boolean apply(T _) {
                return count++ >= num;
            }
        });
    }

    @Override
    public ExtIterable<T> filter(final Pred1<T> predicate) {
        return new ExtIterableImpl<T>(new ImmutableIterator<T>() {
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

    @Override
    public void each(final Proc1<T> proc) {
        while (iterator.hasNext())
            proc.apply(iterator.next());
    }

    @Override
    public Iterator<T> iterator() {
        return this.iterator;
    }

    @Override
    public <Z> ExtIterable<Z> map(final Func1<T, Z> map) {
        return new ExtIterableImpl<Z>(new ImmutableIterator<Z>() {
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

    @Override
    public <A, B> ExtIterable<Tuple2<A, B>> map(final Func1<T, A> a, final Func1<T, B> b) {
        return new ExtIterableImpl<Tuple2<A, B>>(new ImmutableIterator<Tuple2<A, B>>() {
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

    @Override
    public <A, B, C> ExtIterable<Tuple3<A, B, C>> map(final Func1<T, A> a, final Func1<T, B> b, final Func1<T, C> c) {
        return new ExtIterableImpl<Tuple3<A, B, C>>(new ImmutableIterator<Tuple3<A, B, C>>() {
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

    @Override
    public <A, B, C, D> ExtIterable<Tuple4<A, B, C, D>> map(final Func1<T, A> a, final Func1<T, B> b, final Func1<T, C> c, final Func1<T, D> d) {
        return new ExtIterableImpl<Tuple4<A, B, C, D>>(new ImmutableIterator<Tuple4<A, B, C, D>>() {
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

    @Override
    @SuppressWarnings("unchecked")
    public ExtIterable<T> sort() {
        List list = as(new ArrayList());
        Collections.sort(list);
        return new ExtIterableImpl<T>(list);
    }

    @Override
    public ExtIterable<T> sort(final Comparator<T> comparator) {
        List<T> list = as(new ArrayList<T>());
        Collections.sort(list, comparator);
        return new ExtIterableImpl<T>(list);
    }

    @Override
    public <X extends Collection<T>> X as(X container) {
        while (iterator.hasNext())
            container.add(iterator.next());

        return container;
    }

    @Override
    public ExtIterable<T> take(final long num) {
        return new ExtIterableImpl<T>(new ImmutableIterator<T>() {
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

    public ExtIterable<String> toStrings() {
        return new ExtIterableImpl<String>(new ImmutableIterator<String>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public String next() {
                return iterator.next().toString();
            }
        });
    }

    @Override
    public ExtIterable<T> wrap() {
        return new ExtIterableImpl<T>(new ImmutableIterator<T>() {
            Collection<T> collection = new LinkedList<T>();
            boolean isLoaded = false;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                T x;
                if (iterator.hasNext()) {
                    x = iterator.next();
                    if (!isLoaded)
                        collection.add(x);
                } else {
                    isLoaded = true;
                    iterator = collection.iterator();
                    x = iterator.next();
                }
                return x;
            }
        });
    }

    @Override
    public <S> ExtIterable<Tuple2<T, S>> zip(final Iterable<S> xs) {
        final Iterator<S> it = xs.iterator();
        return new ExtIterableImpl<Tuple2<T, S>>(new ImmutableIterator<Tuple2<T, S>>() {
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

    @Override
    public <A, B> ExtIterable<Tuple3<T, A, B>> zip(final Iterable<A> a, final Iterable<B> b) {
        final Iterator<A> a_ = a.iterator();
        final Iterator<B> b_ = b.iterator();
        return new ExtIterableImpl<Tuple3<T, A, B>>(new ImmutableIterator<Tuple3<T, A, B>>() {
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

    @Override
    public <A, B, C> ExtIterable<Tuple4<T, A, B, C>> zip(final Iterable<A> a, final Iterable<B> b, final Iterable<C> c) {
        final Iterator<A> a_ = a.iterator();
        final Iterator<B> b_ = b.iterator();
        final Iterator<C> c_ = c.iterator();
        return new ExtIterableImpl<Tuple4<T, A, B, C>>(new ImmutableIterator<Tuple4<T, A, B, C>>() {
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
