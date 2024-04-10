# <font style='font-size:40px'>VOID-DATA-STRUCTURE 数据结构</font>

**Author:Toutatis_Gc**

![banner](./../../../docs/assets/images/banner.jpg)

## Part.1 数据结构

### 1. 树结构

​	树是一种非常重要和常用的数据结构，它在计算机科学和软件工程领域有着广泛的应用。通过树的结构，可以高效地组织和处理各种数据和信息，实现各种复杂的算法和功能。

#### 树结构基本概念

​	树（Tree）是一种抽象数据结构，它由一组节点（Node）和连接这些节点的边（Edge）组成。树是一种层次结构，由根节点（Root Node）开始，通过边连接到其他节点，形成分层的结构。

1. **根节点（Root Node）**：树的顶部节点，没有父节点，是树的起始点。
2. **父节点（Parent Node）**：一个节点的直接上层节点。
3. **子节点（Child Node）**：一个节点的直接下层节点。
4. **叶子节点（Leaf Node）**：没有子节点的节点称为叶子节点，也称为终端节点。
5. **内部节点（Internal Node）**：除了根节点和叶子节点外的所有节点称为内部节点。
6. **路径（Path）**：从一个节点到另一个节点的边的序列。
7. **深度（Depth）**：从根节点到某个节点的唯一路径上的边的数量。
8. **高度（Height）**：树中任意节点的最大深度。

#### 常见树类型

1. **二叉树（Binary Tree）**：每个节点最多有两个子节点的树。
2. **二叉搜索树（Binary Search Tree，BST）**：一种特殊的二叉树，左子树上的所有节点的值都小于根节点的值，右子树上的所有节点的值都大于根节点的值。
3. **平衡树（Balanced Tree）**：树中任意节点的左右子树高度差不超过 1 的树。
4. **B树（B-Tree）**：一种自平衡的树结构，通常用于数据库和文件系统的实现。
5. **红黑树（Red-Black Tree）**：一种自平衡的二叉搜索树，确保了树的高度近似最小。
6. **字典树（Trie Tree）**：一种多叉树结构，用于存储和检索字符串集合，通常用于字符串相关的算法和应用中。

#### <1> 字典树 TireNode

<div style='border:2px solid green;padding:4px;border-radius:4px;'>
    <span><b>应用领域:</b></span>
    <font color='white' style='background-color:grey;border-radius:4px;padding:4px;margin-right:4px'>
        字符串匹配
    </font>
    <font color='white' style='background-color:grey;border-radius:4px;padding:4px;margin-right:4px'>
        单词预测
    </font>
    <font color='white' style='background-color:grey;border-radius:4px;padding:4px;margin-right:4px'>
        词频统计
    </font>
    <font color='white' style='background-color:grey;border-radius:4px;padding:4px;margin-right:4px'>
        路由表
    </font>
    <font color='white' style='background-color:grey;border-radius:4px;padding:4px;margin-right:4px'>
        数据压缩
    </font>
</div>

​	**字典树（Trie 树）**是一种多叉树形数据结构，用于高效地存储和检索字符串集合。

> 它的名称来自于英文单词"trie"，是"retrieval"的缩写，表示检索。

##### 结构特点：

1. **树形结构**：字典树是一种树形结构，每个节点代表一个字符，从根节点到叶子节点的路径构成一个字符串。

2. **节点关系**：每个节点可以有多个子节点，代表当前字符后面可以跟随的字符。这种关系通常使用指针或数组来表示。

3. **单词结束标记**：通常，每个节点会标记是否是一个单词的结束。这样，在搜索或插入时可以方便地确定一个字符串是否存在于集合中。

    本框架实现使用Boolean标记节点是否结束。

<div align='center'>
    <font><b>简易Trie 树</b></font>
</div>

```basic
            (root)
              /
            'h'
           /    \
         'e'     'i'
        /        |  \
      'l'       'y'  's'
     /   \       |
   'l'    'y'   'e'
  /   \    |    /
'o'   'y' 'o' 'a'
```

## Part.2 算法

### 确定有限自动机

<div style='border:2px solid green;padding:4px;border-radius:4px;'>
    <span><b>应用领域:</b></span>
    <font color='white' style='background-color:grey;border-radius:4px;padding:4px;margin-right:4px'>
        字符串匹配
    </font>
    <font color='white' style='background-color:grey;border-radius:4px;padding:4px;margin-right:4px'>
        语法分析
    </font>
    <font color='white' style='background-color:grey;border-radius:4px;padding:4px;margin-right:4px'>
        模式识别
    </font>
    <font color='white' style='background-color:grey;border-radius:4px;padding:4px;margin-right:4px'>
        编码器
    </font>
</div>

​	**DFA（Deterministic Finite Automaton，确定有限自动机）**是一种计算模型，它是一种有限状态机（Finite State Machine，FSM），用于描述字符串的匹配过程。

最坏时间复杂度：**O(m*n)**， 其中m是状态转移的平均数量，n为自动机的状态数目。

> DFA 算法是一种通用的计算模型，具有广泛的应用领域。它在字符串处理、文本分析、语言识别等方面有着重要的作用。

**具有以下几个关键要素：**

1. **状态集合（States）：** DFA 由一组状态组成，每个状态代表了在字符串匹配过程中的某种状态。初始状态表示开始匹配，终止状态表示匹配成功。
2. **转移函数（Transition Function）：** DFA 在每个状态下根据输入字符进行状态转移，转移函数定义了从一个状态到另一个状态的转移规则。具体而言，对于给定状态和输入字符，转移函数指定了下一个状态是什么。
3. **输入字母表（Alphabet）：** DFA 的输入是来自于一个有限的字母表，每个字符都是字母表中的一个元素。
4. **终止状态（Accepting States）：** 终止状态是 DFA 中的一种特殊状态，表示字符串匹配成功。当 DFA 在某个状态下接受输入后到达终止状态，即表示输入字符串是匹配的。

**DFA 的工作过程如下：**

- 初始时，DFA 处于初始状态。
- 对于输入字符串中的每个字符，根据当前状态和输入字符，通过转移函数确定下一个状态。
- 如果无法进行转移（即没有匹配的转移规则），则匹配失败。
- 如果匹配成功且达到了终止状态，则输入字符串是匹配的。

<div align='center'>
    <font><b>DFA有向状态图</b></font>
</div>

```basic
    ┌───a───→ (q1)───b───→ (q2)   
    │           │
 (start)        c
    │           │
    └───d───→ (q3)───e─→(q4)───→(q5)───→(accept)
```

## Part.3 实现功能

### 敏感词过滤器

​	敏感词过滤器是一种用于检测文本中是否包含敏感词汇的工具。它基于**字典树**和**DFA**实现，能够高效地对输入文本进行扫描和检测，以识别和过滤出现的敏感词汇。

**敏感词过滤器的实现基于以下两个关键技术：**

- **字典树（Trie 树）**：字典树是一种多叉树结构，用于存储字符串集合。在敏感词过滤器中，字典树用于存储敏感词汇的集合，每个节点代表一个字符，从根节点开始到叶子节点代表一个完整的敏感词。
- **DFA（Deterministic Finite Automaton）**：DFA 是一种有限状态自动机，用于识别输入字符串是否符合给定的模式。在敏感词过滤器中，DFA 用于从输入文本中检测是否存在敏感词汇。

**下面是敏感词过滤器的主要实现步骤：**

1. **构建字典树**：将敏感词汇集合构建成字典树，其中每个节点代表一个字符。通过遍历所有敏感词，逐个字符地插入到字典树中。
2. **扫描文本状态转移**：遍历待检测的文本，从第一个字符开始，根据 DFA 的状态转移逐步匹配字符，直到遍历完整个文本。
3. **检测敏感词**：根据 DFA 的状态转移结果，判断是否存在匹配的敏感词。如果某个状态对应的字符是敏感词的结尾字符，则表示检测到了一个敏感词。
4. **过滤敏感词**：根据需要，可以将检测到的敏感词进行替换、屏蔽或其他处理，以实现敏感词的过滤。

**为什么不直接使用String.contains()方法:**

​	Java 中的 `String.contains()` 方法用于检查一个字符串是否包含另一个字符串。这个方法的原理其实很简单，它遍历了调用它的字符串的每一个字符，然后在其中查找要搜索的字符串。

​	具体来说，这个方法使用了朴素的字符串搜索算法，也称为**暴力搜索算法（Brute Force Algorithm）**。这个算法的基本思想是，在目标字符串中，从左到右逐个字符地和待搜索的字符串进行匹配，如果发现匹配失败，就将搜索位置向右移动一位，继续匹配，直到找到匹配或者搜索完整个目标字符串。

​	这种搜索算法的时间复杂度是**O(m*n)**，其中m是目标字符串的长度，n是要搜索的字符串的长度。因此，对于较大的字符串或者较长的要搜索的字符串，这种方法效率不高。
