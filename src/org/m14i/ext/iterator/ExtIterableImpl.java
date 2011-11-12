package org.m14i.ext.iterator;

import org.m14i.ext.methods.Func1;
import org.m14i.ext.methods.Func2;
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
    public boolean all(final Func1<T, Boolean> predicate) {
        Func1<T, Boolean> func = new Func1<T, Boolean>() {
            @Override
            public Boolean apply(T arg) {
                return !predicate.apply(arg);
            }
        };
        return !any(func);
    }

    @Override
    public boolean any(final Func1<T, Boolean> predicate) {
        while (iterator.hasNext()) {
            if (predicate.apply(iterator.next())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <X extends Collection<T>> X as(X container) {
        while (iterator.hasNext()) {
            container.add(iterator.next());
        }
        return container;
    }

    @Override
    public long count(final Func1<T, Boolean> predicate) {
        return reduce(0L, new Func2<Long, T, Long>() {
            @Override
            public Long apply(Long carry, T item) {
                return carry + (predicate.apply(item) ? 1L : 0L);
            }
        });
    }

    @Override
    public <Z> Z reduce(final Z initial, final Func2<Z, T, Z> reduce) {
        Z carry = initial;
        while (iterator.hasNext()) {
            carry = reduce.apply(carry, iterator.next());
        }
        return carry;
    }

    @Override
    public ExtIterable<T> drop(final long num) {
        return filter(new Func1<T, Boolean>() {
            long count = 0;

            @Override
            public Boolean apply(T arg) {
                return count++ >= num;
            }
        });
    }

    @Override
    public ExtIterable<T> filter(final Func1<T, Boolean> predicate) {
        return new ExtIterableImpl<T>(new ImmutableIterator<T>() {
            T nextItem = findNextItem();

            T findNextItem() {
                while (iterator.hasNext()) {
                    T item = iterator.next();
                    if (predicate.apply(item)) {
                        return item;
                    }
                }
                return null;
            }

            @Override
            public boolean hasNext() {
                return nextItem != null;
            }

            @Override
            public T next() {
                T current = nextItem;
                nextItem = findNextItem();
                return current;
            }
        });
    }

    @Override
    public void each(final Proc1<T> proc) {
        while (iterator.hasNext()) {
            proc.apply(iterator.next());
        }
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
                T item = iterator.next();
                return new Tuple2<A, B>(a.apply(item), b.apply(item));
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
                T item = iterator.next();
                return new Tuple3<A, B, C>(a.apply(item), b.apply(item), c.apply(item));
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
                T item = iterator.next();
                return new Tuple4<A, B, C, D>(a.apply(item), b.apply(item), c.apply(item), d.apply(item));
            }
        });
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
                T item;
                if (iterator.hasNext()) {
                    item = iterator.next();
                    if (!isLoaded) {
                        collection.add(item);
                    }
                } else {
                    isLoaded = true;
                    iterator = collection.iterator();
                    item = iterator.next();
                }
                return item;
            }
        });
    }

    @Override
    public <S> ExtIterable<Tuple2<T, S>> zip(final Iterable<S> items) {
        final Iterator<S> it = items.iterator();
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
