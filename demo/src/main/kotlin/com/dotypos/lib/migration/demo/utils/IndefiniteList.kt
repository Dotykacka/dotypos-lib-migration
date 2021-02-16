package com.dotypos.lib.migration.demo.utils

class IndefiniteList<T>(private val data: List<T>): List<T> {
    override val size = Int.MAX_VALUE

    override fun contains(element: T) = data.contains(element)

    override fun containsAll(elements: Collection<T>) = data.containsAll(elements)

    override fun get(index: Int) = data.get(index % data.size)

    override fun indexOf(element: T) = data.indexOf(element)

    override fun isEmpty() = data.isEmpty()

    override fun iterator() = data.iterator()

    override fun lastIndexOf(element: T) = data.lastIndexOf(element)

    override fun listIterator() = data.listIterator()

    override fun listIterator(index: Int) = data.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> {
        TODO("Not implemented yet")
    }

}