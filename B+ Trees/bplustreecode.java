public class bplustreecode {

    public static void main(String[] args) {
        BPlusTree bPlusTree = new BPlusTree(3);
        bPlusTree.insert(1);
        bPlusTree.insert(4);
        bPlusTree.insert(7);
        bPlusTree.insert(10);
        bPlusTree.insert(17);
        bPlusTree.insert(21);
        bPlusTree.insert(31);
        bPlusTree.insert(25);
        bPlusTree.insert(19);
        bPlusTree.insert(20);
        bPlusTree.insert(28);
        bPlusTree.insert(42);
        System.out.println(bPlusTree.root.curCount);

        bPlusTree.delete(42);
        bPlusTree.delete(25);
        bPlusTree.delete(20);
        bPlusTree.delete(21);
        bPlusTree.delete(10);
        bPlusTree.delete(31);

        System.out.println(bPlusTree.root.curCount);
    }

    public static class Node
    {
        int[] keys;
        Node[] childs;
        int max;
        int curCount;
        boolean isLeaf;
        Node next;

        public Node(int t)
        {
            this.keys = new int[2*t-1];
            this.childs = new Node[2*t];
            this.max = 2*t-1;
            this.curCount = 0;
            this.isLeaf = true;
        }
    }

    public static class BPlusTree {
        public int t;
        Node root;

        BPlusTree(int t) {
            this.t = t;
            this.root = new Node(t);
        }

        void insert(int k) {
            //First check if root is full

            if (root.curCount == 2 * t - 1) {
                Node newRoot = new Node(t);
                newRoot.childs[0] = root;
                newRoot.isLeaf = false;
                split(newRoot, root, 0,k);

                root = newRoot;
            }
            insertNonFullNode(root, k);
        }

        void delete(int k)
        {
            delete(this.root,k);
        }

        void insertNonFullNode(Node root, int k)
        {
            int  i =root.curCount-1;
            if(root.isLeaf)
            {
                while (i >= 0 && root.keys[i] > k)
                {
                    root.keys[i+1] = root.keys[i];
                    i--;
                }

                // Insert the new key at found location
                root.keys[i+1] = k;
                root.curCount = root.curCount+1;
            }
            else
            {
                while (i >= 0 && root.keys[i] > k)
                    i--;
                if (root.childs[i+1].curCount == 2*t-1)
                {
                    split(root,root.childs[i+1],i+1,k);
                    if (root.keys[i+1]<=k)
                        i++;
                }
                insertNonFullNode(root.childs[i+1],k);
            }
        }

        void split(Node cur, Node childFullNode, int ind, int k)
        {
            Node newNode = new Node(t);
            newNode.isLeaf = childFullNode.isLeaf;
            childFullNode.next = newNode;
            for(int i = (2*t-1)-1;i>t-1;i--)
            {
                newNode.keys[i-t] = childFullNode.keys[i];
            }
            if(!newNode.isLeaf)
            {
                for(int i = (2*t)-1;i>t-1;i--)
                {
                    newNode.childs[i-t] = childFullNode.childs[i];
                }
            }
            childFullNode.curCount = t;
            newNode.curCount = t-1;

            for(int i = ind+1;i<cur.childs.length-1;i++)
                cur.childs[i+1] = cur.childs[i];
            cur.childs[ind+1] = newNode;

            for(int i = ind;i<cur.keys.length-1;i++)
                cur.keys[i+1] = cur.keys[i];

            cur.keys[ind] = newNode.keys[0];
            cur.curCount+=1;
        }
        void delete(Node root, int k)
        {
            if(root.isLeaf)
            {
                int i = 0;
                for(;i<root.curCount;i++)
                {
                    if(root.keys[i]==k)
                        break;
                }
                for(;i<root.curCount-1;i++)
                {
                    root.keys[i] = root.keys[i+1];
                }
                root.curCount-=1;
            }
            else
            {
                int i = 0;
                while(i<root.curCount && root.keys[i]<k)
                    i++;
                if(i<root.curCount && root.keys[i] == k)
                {
                    if(i+1<=root.curCount &&  root.childs[i+1].curCount>t-1)
                    {
                        root.keys[i] = findSuccessor(root.childs[i+1]);
                        delete(root.childs[i+1],k);
                    }
                    else
                    {
                        merge(root,root.childs[i],root.childs[i+1],i);
                        delete(root.childs[i],k);
                    }
                }
                else
                {
                    if(root.childs[i].curCount==t-1)
                    {
                        if(i-1>=0 && root.childs[i-1].curCount>=t)
                        {
                            //rotateRight
                            rotateRight(root,root.childs[i-1],root.childs[i],i-1);
                            delete(root.childs[i-1],k);
                        }
                        else if(i<root.curCount && root.childs[i+1].curCount>=t)
                        {
                            //leftRotate
                            rotateLeft(root,root.childs[i],root.childs[i+1],i);
                            delete(root.childs[i],k);
                        }
                        else
                        {
                            //Merge
                            if(i<root.curCount)
                            {
                                merge(root,root.childs[i],root.childs[i+1],i);
                                delete(root.childs[i],k);
                            }
                            else
                            {
                                merge(root,root.childs[i-1],root.childs[i],i-1);
                                delete(root.childs[i-1],k);
                            }
                        }
                    }
                    else
                        delete(root.childs[i],k);
                }
            }
        }

        public int findSuccessor(Node child)
        {
            while (!child.isLeaf)
                child = child.childs[0];
            return child.keys[1];
        }

        public void merge(Node root, Node child1, Node child2, int indRoot)
        {
            for(int i = child1.curCount,j = 0;i<2*t-1;i++,j++)
                child1.keys[i] = child2.keys[j];
            for(int i = child1.curCount;i<2*t-1;i++)
                child1.childs[i+1] = child2.childs[i];

            for(int i = indRoot;i<root.curCount-1;i++)
                root.keys[i] = root.keys[i+1];
            for(int i = indRoot+1;i<root.curCount;i++)
                root.childs[i] = root.childs[i+1];

            root.curCount-=1;
            child1.curCount = child1.curCount+child2.curCount;

            if(root == this.root && root.curCount == 0)
                this.root = child1;

        }

        public void rotateRight(Node root, Node child1, Node child2, int indRoot)
        {
            for(int i = 0;i<child2.curCount;i++)
                child2.keys[i+1] = child2.keys[i];
            for(int i = 0;i<=child2.curCount;i++)
                child2.childs[i+1] = child2.childs[i];
            root.keys[indRoot] = child1.keys[child1.curCount-1];
            child2.keys[0] = root.keys[indRoot];
            child2.childs[0] = child1.childs[child1.curCount];

            child1.curCount-=1;
            child2.curCount+=1;
        }

        public void rotateLeft(Node root, Node child1, Node child2, int indRoot)
        {
            child1.keys[child1.curCount] = root.keys[indRoot];
            root.keys[indRoot] = child2.keys[1];
            child1.childs[child1.curCount+1] = child2.childs[0];
            for(int i = 1;i<child2.curCount;i++)
                child2.keys[i-1] = child2.keys[i];
            for(int i = 0;i<=child2.curCount;i++)
                child2.childs[i-1] = child2.childs[i];

            child1.curCount+=1;
            child2.curCount-=1;
        }
    }
}
