package cn.toutatis.xvoid.orm.structure

/**
 * Tree
 * @param T 树类型
 * @param N 节点类型
 * @constructor Create empty Tree
 */
interface Tree<T,N:Node> {

    /**
     * Get parent
     * @return
     */
    fun getParent():Tree<T,N>

    /**
     * Get branch
     * @return
     */
    fun getBranch():List<Tree<T,N>>

    /**
     * Get nodes
     * @return
     */
    fun getNodes():List<N>;

}