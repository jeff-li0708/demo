package algorithm;

/**
 * 红黑树实现：
 * RBTree的定义如下:
 * 1. 任何一个节点都有颜色，黑色或者红色
 * 2. 根节点是黑色的
 * 3. 父子节点之间不能出现两个连续的红节点
 * 4. 任何一个节点向下遍历到其子孙的叶子节点，所经过的黑节点个数必须相等
 * 5. 空节点被认为是黑色的
 *
 * 新插入的节点是红色的，插入修复操作如果遇到父节点的颜色为黑则修复操作结束。也就是说，只有在父节点为红色节点的时候是需要插入修复操作的。
 * 插入分三种情况：
 * 1. 叔叔节点也为红色
 * 2. 叔叔节点为空，且祖父节点、父节点和新节点处于一条斜线上
 * 3. 叔叔节点为空，且祖父节点、父节点和新节点不处于一条斜线上
 *
 * case 1的操作是将父节点和叔叔节点与祖父节点的颜色互换，这样就符合了RBTRee的定义。即维持了高度的平衡，修复后颜色也符合RBTree定义的第三条和第四条
 * case 2的操作是将B节点进行右旋操作，并且和父节点A互换颜色。通过该修复操作RBTRee的高度和颜色都符合红黑树的定义。如果B和C节点都是右节点的话，只要将操作变成左旋就可以了。
 * case 3的操作是将C节点进行左旋，这样就从case 3转换成case 2了，然后针对case 2进行操作处理就行了。case 2操作做了一个右旋操作和颜色互换来达到目的。如果树的结构是下图的镜像结构，则只需要将对应的左旋变成右旋，右旋变成左旋即可。
 *
 *
 * 删除修复操作分为四种情况(删除黑节点后)：
 * 1. 待删除的节点的兄弟节点是红色的节点（兄弟是红色）
 * 2. 待删除的节点的兄弟节点是黑色的节点，且兄弟节点的子节点都是黑色的（兄弟是黑色，且兄弟的儿子是黑色）
 * 3. 待调整的节点的兄弟节点是黑色的节点，且兄弟节点的左子节点是红色的，右节点是黑色的(兄弟节点在右边)，如果兄弟节点在左边的话，就是兄弟节点的右子节点是红色的，左节点是黑色的（兄弟是黑色，且兄弟的靠近自己这边的儿子是红色）
 * 4. 待调整的节点的兄弟节点是黑色的节点，且右子节点是是红色的(兄弟节点在右边)，如果兄弟节点在左边，则就是对应的就是左节点是红色的。（兄弟是黑色，且兄弟的没靠近自己这边的儿子是红色）
 * 删除操作-case 1
 * 由于兄弟节点是红色节点的时候，无法借调黑节点，所以需要将兄弟节点提升到父节点，由于兄弟节点是红色的，根据RBTree的定义，兄弟节点的子节点是黑色的，就可以从它的子节点借调了。
 * case 1这样转换之后就会变成后面的case 2，case 3，或者case 4进行处理了。上升操作需要对C做一个左旋操作，如果是镜像结构的树只需要做对应的右旋操作即可。
 * 之所以要做case 1操作是因为兄弟节点是红色的，无法借到一个黑节点来填补删除的黑节点。
 *
 * 删除操作-case 2
 * case 2的删除操作是由于兄弟节点可以消除一个黑色节点，因为兄弟节点和兄弟节点的子节点都是黑色的，所以可以将兄弟节点变红，这样就可以保证树的局部的颜色符合定义了。这个时候需要将父节点A变成新的节点，继续向上调整，直到整颗树的颜色符合RBTree的定义为止。
 * case 2这种情况下之所以要将兄弟节点变红，是因为如果把兄弟节点借调过来，会导致兄弟的结构不符合RBTree的定义，这样的情况下只能是将兄弟节点也变成红色来达到颜色的平衡。当将兄弟节点也变红之后，达到了局部的平衡了，但是对于祖父节点来说是不符合定义4的。这样就需要回溯到父节点，接着进行修复操作。
 *
 * 删除操作-case 3
 * case 3的删除操作是一个中间步骤，它的目的是将左边的红色节点借调过来，这样就可以转换成case 4状态了，在case 4状态下可以将D，E节点都阶段过来，通过将两个节点变成黑色来保证红黑树的整体平衡。
 * 之所以说case-3是一个中间状态，是因为根据红黑树的定义来说，下图并不是平衡的，他是通过case 2操作完后向上回溯出现的状态。之所以会出现case 3和后面的case 4的情况，是因为可以通过借用侄子节点的红色，变成黑色来符合红黑树定义4.
 * 上面的左旋右旋是基于节点B，对其父节点而言
 *
 * 删除操作-case 4
 * Case 4的操作是真正的节点借调操作，通过将兄弟节点以及兄弟节点的右节点借调过来，并将兄弟节点的右子节点变成红色来达到借调两个黑节点的目的，这样的话，整棵树还是符合RBTree的定义的。
 * Case 4这种情况的发生只有在待删除的节点的兄弟节点为黑，且子节点不全部为黑，才有可能借调到两个节点来做黑节点使用，从而保持整棵树都符合红黑树的定义。
 */
public class BRTreeTest {
    //节点
    static class RBTreeNode<T extends Comparable<T>> {
        private T value;//node value
        private RBTreeNode<T> left;//left child pointer
        private RBTreeNode<T> right;//right child pointer
        private RBTreeNode<T> parent;//parent pointer
        private boolean red;//color is red or not red

        public RBTreeNode(){}
        public RBTreeNode(T value){this.value=value;}
        public RBTreeNode(T value,boolean isRed){this.value=value;this.red = isRed;}

        public T getValue() {
            return value;
        }
        void setValue(T value) {
            this.value = value;
        }
        RBTreeNode<T> getLeft() {
            return left;
        }
        void setLeft(RBTreeNode<T> left) {
            this.left = left;
        }
        RBTreeNode<T> getRight() {
            return right;
        }
        void setRight(RBTreeNode<T> right) {
            this.right = right;
        }
        RBTreeNode<T> getParent() {
            return parent;
        }
        void setParent(RBTreeNode<T> parent) {
            this.parent = parent;
        }
        boolean isRed() {
            return red;
        }
        boolean isBlack(){
            return !red;
        }
        /**
         * is leaf node
         **/
        boolean isLeaf(){
            return left==null && right==null;
        }

        void setRed(boolean red) {
            this.red = red;
        }

        void makeRed(){
            red=true;
        }
        void makeBlack(){
            red=false;
        }
        @Override
        public String toString(){
            return value.toString();
        }
    }

    static class RBTree<T extends Comparable<T>> {
        private final RBTreeNode<T> root;
        //node number
        private java.util.concurrent.atomic.AtomicLong size =
                new java.util.concurrent.atomic.AtomicLong(0);

        //in overwrite mode,all node's value can not  has same	value
        //in non-overwrite mode,node can have same value, suggest don't use non-overwrite mode.
        private volatile boolean overrideMode = true;

        public RBTree() {
            this.root = new RBTreeNode<T>();
        }

        public RBTree(boolean overrideMode) {
            this();
            this.overrideMode = overrideMode;
        }


        public boolean isOverrideMode() {
            return overrideMode;
        }

        public void setOverrideMode(boolean overrideMode) {
            this.overrideMode = overrideMode;
        }

        /**
         * number of tree number
         *
         * @return
         */
        public long getSize() {
            return size.get();
        }

        /**
         * get the root node
         *
         * @return
         */
        private RBTreeNode<T> getRoot() {
            return root.getLeft();
        }

        /**
         * add value to a new node,if this value exist in this tree,
         * if value exist,it will return the exist value.otherwise return null
         * if override mode is true,if value exist in the tree,
         * it will override the old value in the tree
         *
         * @param value
         * @return
         */
        public T addNode(T value) {
            RBTreeNode<T> t = new RBTreeNode<T>(value);
            return addNode(t);
        }

        /**
         * find the value by give value(include key,key used for search,
         * other field is not used,@see compare method).if this value not exist return null
         *
         * @param value
         * @return
         */
        public T find(T value) {
            RBTreeNode<T> dataRoot = getRoot();
            while (dataRoot != null) {
                int cmp = dataRoot.getValue().compareTo(value);
                if (cmp < 0) {
                    dataRoot = dataRoot.getRight();
                } else if (cmp > 0) {
                    dataRoot = dataRoot.getLeft();
                } else {
                    return dataRoot.getValue();
                }
            }
            return null;
        }

        /**
         * remove the node by give value,if this value not exists in tree return null
         *
         * @param value include search key
         * @return the value contain in the removed node
         */
        public T remove(T value) {
            RBTreeNode<T> dataRoot = getRoot();
            RBTreeNode<T> parent = root;

            while (dataRoot != null) {
                int cmp = dataRoot.getValue().compareTo(value);
                if (cmp < 0) {//要删除的值比当前节点大
                    parent = dataRoot;
                    dataRoot = dataRoot.getRight();
                } else if (cmp > 0) {//要删除的值比当前节点小
                    parent = dataRoot;
                    dataRoot = dataRoot.getLeft();
                } else {//找到了要删除的节点dataRoot
                    if (dataRoot.getRight() != null) { //要删除的节点右节点不为空
                        RBTreeNode<T> min = removeMin(dataRoot.getRight());//拿到要删除节点的右节点的最小叶子节点
                        //x used for fix color balance
                        RBTreeNode<T> x = min.getRight() == null ? min.getParent() : min.getRight();//min有有节点取有节点，没右节点取父节点，然后赋值给x
                        boolean isParent = min.getRight() == null;

                        //min替换要删除节点的位置
                        min.setLeft(dataRoot.getLeft());//要删除的节点的左赋值给右的最左（也就是左右子树合并）
                        setParent(dataRoot.getLeft(), min);
                        if (parent.getLeft() == dataRoot) {//要删的节点在左边
                            parent.setLeft(min);
                        } else {
                            parent.setRight(min);
                        }
                        setParent(min, parent);

                        boolean curMinIsBlack = min.isBlack();
                        //inherit dataRoot's color
                        min.setRed(dataRoot.isRed());//继承要删除节点的颜色

                        if (min != dataRoot.getRight()) { //将删除节点的右子树赋值给替补节点的右子树
                            min.setRight(dataRoot.getRight());
                            setParent(dataRoot.getRight(), min);
                        }
                        //替补的节点是黑色，需要修复
                        if (curMinIsBlack) {
                            if (min != dataRoot.getRight()) {
                                fixRemove(x, isParent);
                            } else if (min.getRight() != null) {
                                fixRemove(min.getRight(), false);
                            } else {
                                fixRemove(min, true);
                            }
                        }
                    } else {//要删除的节点右节点为空
                        //用要删除的节点的子节点替换要删除节点的位置
                        setParent(dataRoot.getLeft(), parent);
                        if (parent.getLeft() == dataRoot) {
                            parent.setLeft(dataRoot.getLeft());
                        } else {
                            parent.setRight(dataRoot.getLeft());
                        }

                        //current node is black and tree is not empty
                        if (dataRoot.isBlack() && !(root.getLeft() == null)) {
                            RBTreeNode<T> x = dataRoot.getLeft() == null
                                    ? parent : dataRoot.getLeft();
                            boolean isParent = dataRoot.getLeft() == null;
                            fixRemove(x, isParent);
                        }
                    }
                    setParent(dataRoot, null);
                    dataRoot.setLeft(null);
                    dataRoot.setRight(null);
                    if (getRoot() != null) {
                        getRoot().setRed(false);
                        getRoot().setParent(null);
                    }
                    size.decrementAndGet();
                    return dataRoot.getValue();
                }
            }
            return null;
        }

        /**
         * fix remove action
         * 删除的是黑色节点才需要修复
         *
         * @param node
         * @param isParent
         */
        private void fixRemove(RBTreeNode<T> node, boolean isParent) {
            RBTreeNode<T> cur = isParent ? null : node;
            boolean isRed = isParent ? false : node.isRed();
            RBTreeNode<T> parent = isParent ? node : node.getParent();

            while (!isRed && !isRoot(cur)) {
                RBTreeNode<T> sibling = getSibling(cur, parent);
                //sibling is not null,due to before remove tree color is balance

                //if cur is a left node
                boolean isLeft = parent.getRight() == sibling;
                if (sibling.isRed() && !isLeft) {//case 1
                    //cur in right
                    parent.makeRed();
                    sibling.makeBlack();
                    rotateRight(parent);
                } else if (sibling.isRed() && isLeft) {
                    //cur in left
                    parent.makeRed();
                    sibling.makeBlack();
                    rotateLeft(parent);
                } else if (isBlack(sibling.getLeft()) && isBlack(sibling.getRight())) {//case 2
                    sibling.makeRed();
                    cur = parent;
                    isRed = cur.isRed();
                    parent = parent.getParent();
                } else if (isLeft && !isBlack(sibling.getLeft())
                        && isBlack(sibling.getRight())) {//case 3
                    sibling.makeRed();
                    sibling.getLeft().makeBlack();
                    rotateRight(sibling);
                } else if (!isLeft && !isBlack(sibling.getRight())
                        && isBlack(sibling.getLeft())) {
                    sibling.makeRed();
                    sibling.getRight().makeBlack();
                    rotateLeft(sibling);
                } else if (isLeft && !isBlack(sibling.getRight())) {//case 4
                    sibling.setRed(parent.isRed());
                    parent.makeBlack();
                    sibling.getRight().makeBlack();
                    rotateLeft(parent);
                    cur = getRoot();
                } else if (!isLeft && !isBlack(sibling.getLeft())) {
                    sibling.setRed(parent.isRed());
                    parent.makeBlack();
                    sibling.getLeft().makeBlack();
                    rotateRight(parent);
                    cur = getRoot();
                }
            }
            if (isRed) {
                cur.makeBlack();
            }
            if (getRoot() != null) {
                getRoot().setRed(false);
                getRoot().setParent(null);
            }

        }

        //get sibling node
        private RBTreeNode<T> getSibling(RBTreeNode<T> node, RBTreeNode<T> parent) {
            parent = node == null ? parent : node.getParent();
            if (node == null) {
                return parent.getLeft() == null ? parent.getRight() : parent.getLeft();
            }
            if (node == parent.getLeft()) {
                return parent.getRight();
            } else {
                return parent.getLeft();
            }
        }

        private boolean isBlack(RBTreeNode<T> node) {
            return node == null || node.isBlack();
        }

        private boolean isRoot(RBTreeNode<T> node) {
            return root.getLeft() == node && node.getParent() == null;
        }

        /**
         * 删除并返回最小的节点（也就是最左叶子）
         *
         * @param node current node's right node
         * @return
         */
        private RBTreeNode<T> removeMin(RBTreeNode<T> node) {
            //find the min node
            RBTreeNode<T> parent = node;
            while (node != null && node.getLeft() != null) {
                parent = node;
                node = node.getLeft();
            }
            //remove min node
            if (parent == node) {
                return node;
            }

            parent.setLeft(node.getRight());
            setParent(node.getRight(), parent);

            //don't remove right pointer,it is used for fixed color balance
            //node.setRight(null);
            return node;
        }


        private T addNode(RBTreeNode<T> node) {
            node.setLeft(null);
            node.setRight(null);
            node.setRed(true);
            setParent(node, null);
            if (root.getLeft() == null) {
                root.setLeft(node);
                //root node is black
                node.setRed(false);
                size.incrementAndGet();
            } else {
                RBTreeNode<T> p = findParentNode(node);//查找要添加的节点的父节点
                int cmp = p.getValue().compareTo(node.getValue());

                if (this.overrideMode && cmp == 0) {//允许覆盖，且要添加的值与父节点的值相等
                    T v = p.getValue();
                    p.setValue(node.getValue());
                    return v;
                } else if (cmp == 0) {//值存在忽略这个节点
                    return p.getValue();
                }

                setParent(node, p);//设置要添加的节点的父节点

                if (cmp > 0) {//比父节点小
                    p.setLeft(node);
                } else {//比父节点大
                    p.setRight(node);
                }

                fixInsert(node);//修复操作
                size.incrementAndGet();//节点数+1
            }
            return null;
        }

        /**
         * find the parent node to hold node x,if parent value equals x.value return parent.
         * 找到保存x的父节点，如果父节点的值等于x的值，直接返回父节点
         *
         * @param x
         * @return
         */
        private RBTreeNode<T> findParentNode(RBTreeNode<T> x) {
            RBTreeNode<T> dataRoot = getRoot();
            RBTreeNode<T> child = dataRoot;

            while (child != null) {
                int cmp = child.getValue().compareTo(x.getValue());
                if (cmp == 0) {//x的值等于当前节点的值，返回当前节点
                    return child;
                }
                if (cmp > 0) {//x的值小于当前节点的值,继续比较左子树
                    dataRoot = child;
                    child = child.getLeft();
                } else if (cmp < 0) {//x的值大于当前节点的值,继续比较右子树
                    dataRoot = child;
                    child = child.getRight();
                }
            }
            return dataRoot;//叶子节点
        }

        /**
         * red black tree insert fix.
         *
         * @param node
         */
        private void fixInsert(RBTreeNode<T> node) {
            RBTreeNode<T> parent = node.getParent();

            while (parent != null && parent.isRed()) { //父节点不为空且为红色，也就是父节点为红色时才会修复
                RBTreeNode<T> uncle = getUncle(node);//获取叔叔节点
                if (uncle == null) { //叔叔节点为空，case2和case3
                    RBTreeNode<T> ancestor = parent.getParent();
                    //ancestor is not null due to before before add,tree color is balance
                    if (parent == ancestor.getLeft()) {//父节点是祖先节点的左节点
                        boolean isRight = node == parent.getRight();//当前节点是否是父节点的右节点
                        if (isRight) {
                            rotateLeft(parent);//左旋，旋转之后三个点在一条从左下到右上的直线上
                        }
                        rotateRight(ancestor);//然后将直线右旋

                        //父节点是红色，祖父节点必然是黑色，下面是交换父节点和祖父节点的颜色
                        if (isRight) {
                            node.setRed(false);
                            parent = null;//end loop
                        } else {
                            parent.setRed(false);
                        }
                        ancestor.setRed(true);
                    } else {//父节点是祖先节点的右节点
                        boolean isLeft = node == parent.getLeft();//当前节点是否是父节点的左节点
                        if (isLeft) {
                            rotateRight(parent);//是则先进行右旋，旋转之后三个节点在一条从左上到右下的直线上
                        }
                        rotateLeft(ancestor);//然后将直线左旋

                        //父节点是红色，祖父节点必然是黑色，下面是交换父节点和祖父节点的颜色
                        if (isLeft) {
                            node.setRed(false);
                            parent = null;//end loop
                        } else {
                            parent.setRed(false);
                        }
                        ancestor.setRed(true);
                    }
                } else {//叔叔节点不为空，case1，只需要循环改变父节点的颜色
                    parent.setRed(false);
                    uncle.setRed(false);
                    parent.getParent().setRed(true);
                    node = parent.getParent();
                    parent = node.getParent();
                }
            }//end while
            getRoot().makeBlack();
            getRoot().setParent(null);
        }

        /**
         * 获取叔叔节点
         *
         * @param node
         * @return
         */
        private RBTreeNode<T> getUncle(RBTreeNode<T> node) {
            RBTreeNode<T> parent = node.getParent();//拿到父节点
            RBTreeNode<T> ancestor = parent.getParent();//拿到祖父节点
            if (ancestor == null) {
                return null;
            }
            if (parent == ancestor.getLeft()) {//父节点是祖父节点的左子节点，叔叔节点则为祖父的右子节点
                return ancestor.getRight();
            } else {
                return ancestor.getLeft();//否则叔叔节点为祖父节点的左子节点
            }
        }

        /**
         * 左旋操作，以下面例子为例：
         *    A             A
         *   *             *
         *  B     ===>    C
         *   *           *
         *    C         B
         * @param node
         */
        private void rotateLeft(RBTreeNode<T> node) { //node=B
            RBTreeNode<T> right = node.getRight();//C
            if (right == null) {
                throw new java.lang.IllegalStateException("right node is null");
            }
            RBTreeNode<T> parent = node.getParent();//A
            node.setRight(right.getLeft());//设置B的右为C的左，也就是空
            setParent(right.getLeft(), node);//设置C的左（这里也就是null）的父节点为B

            right.setLeft(node);//设置C的左为B
            setParent(node, right);//设置B的父节点为C

            if (parent == null) {//node pointer to root
                //right  raise to root node
                root.setLeft(right);
                setParent(right, null);
            } else {
                if (parent.getLeft() == node) {
                    parent.setLeft(right);
                } else {
                    parent.setRight(right);
                }
                //right.setParent(parent);
                setParent(right, parent);
            }
        }

        private void rotateRight(RBTreeNode<T> node) {
            RBTreeNode<T> left = node.getLeft();
            if (left == null) {
                throw new java.lang.IllegalStateException("left node is null");
            }
            RBTreeNode<T> parent = node.getParent();
            node.setLeft(left.getRight());
            setParent(left.getRight(), node);

            left.setRight(node);
            setParent(node, left);

            if (parent == null) {
                root.setLeft(left);
                setParent(left, null);
            } else {
                if (parent.getLeft() == node) {
                    parent.setLeft(left);
                } else {
                    parent.setRight(left);
                }
                setParent(left, parent);
            }
        }


        private void setParent(RBTreeNode<T> node, RBTreeNode<T> parent) {
            if (node != null) {
                node.setParent(parent);
                if (parent == root) {
                    node.setParent(null);
                }
            }
        }

        /**
         * debug method,it used print the given node and its children nodes,
         * every layer output in one line
         *
         * @param root
         */
        public void printTree(RBTreeNode<T> root) {
            java.util.LinkedList<RBTreeNode<T>> queue = new java.util.LinkedList<RBTreeNode<T>>();
            java.util.LinkedList<RBTreeNode<T>> queue2 = new java.util.LinkedList<RBTreeNode<T>>();
            if (root == null) {
                return;
            }
            queue.add(root);
            boolean firstQueue = true;

            while (!queue.isEmpty() || !queue2.isEmpty()) {
                java.util.LinkedList<RBTreeNode<T>> q = firstQueue ? queue : queue2;
                RBTreeNode<T> n = q.poll();

                if (n != null) {
                    String pos = n.getParent() == null ? "" : (n == n.getParent().getLeft()
                            ? " LE" : " RI");
                    String pstr = n.getParent() == null ? "" : n.getParent().toString();
                    String cstr = n.isRed() ? "R" : "B";
                    cstr = n.getParent() == null ? cstr : cstr + " ";
                    System.out.print(n + "(" + (cstr) + pstr + (pos) + ")" + "\t");
                    if (n.getLeft() != null) {
                        (firstQueue ? queue2 : queue).add(n.getLeft());
                    }
                    if (n.getRight() != null) {
                        (firstQueue ? queue2 : queue).add(n.getRight());
                    }
                } else {
                    System.out.println();
                    firstQueue = !firstQueue;
                }
            }
        }


    }

    public static void main(String[] args) {
        RBTree<Integer> bst = new RBTree<Integer>();
        bst.addNode(4);
        //bst.addNode(4);
        //bst.addNode(3);
        bst.addNode(3);
        bst.addNode(2);
        bst.addNode(6);

        bst.addNode(1);
        bst.addNode(5);

        bst.addNode(7);
        bst.addNode(8);


        bst.remove(3);

        bst.printTree(bst.getRoot());

    }
}
