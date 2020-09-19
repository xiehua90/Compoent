package com.company.tree

import java.util.*


//排序总结: 先构建一个树 -> 对节点的子节点的集合进行排序 -> 对所有节点进行排序
class Node(val id: String, val parentId: String) {
    companion object {
        @JvmStatic
        fun main() {
            val data = listOf(
                Node("1", ""),
                Node("2", "1"),
                Node("10", "7"),
                Node("11", "3"),
                Node("12", "3"),
                Node("13", "9"),
                Node("3", "2"),
                Node("4", "2"),
                Node("5", "4"),
                Node("6", "4"),
                Node("7", "1"),
                Node("8", "7"),
                Node("9", "8"),
                Node("14", "2")
            )

            val dataMap = mutableMapOf<String, Node>()
            data.forEach {
                dataMap[it.id] = it
            }

            data.forEach {
                val parentNode = dataMap[it.parentId]
                if (parentNode != null) {
                    it.parent = parentNode

                    if (parentNode.children == null) {
                        parentNode.children = mutableListOf()
                    }
                    parentNode.children!!.add(it)
                }
            }

            data.forEach {
                if (it.children != null) {
                    it.children!!.sortWith(Comparator { o1, o2 ->
                        if (o1 == null && o2 == null) {
                            0
                        } else if (o1 == null) {
                            1
                        } else if (o2 == null) {
                            -1
                        } else {
                            Integer.valueOf(o1.id) - Integer.valueOf(o2.id)
                        }
                    })
                }
            }

            val resultList = mutableListOf<Node>();
            dataMap["1"]?.let { sort(it, resultList) }

            resultList.forEach { println(it.id) }
        }

        fun sort(rootNode: Node, list: MutableList<Node>) {
            list.add(rootNode)
            if (rootNode.children != null) {
                for (item in rootNode.children!!) {
                    sort(item, list)
                }
            }
        }
    }

    var parent: Node? = null
    var children: MutableList<Node>? = null
}