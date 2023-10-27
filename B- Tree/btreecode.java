public class btreecode {
    public static void main(String[] args) {
//        BTree bTree = new BTree(3);
//        bTree.insert(10);
//        bTree.insert(20);
//        bTree.insert(5);
//        bTree.insert(6);
//        bTree.insert(12);
//        bTree.insert(30);
//        bTree.insert(7);
//        bTree.insert(17);

        BTree bTree = new BTree(3);


        for(int i = 1;i<10;i++)
            bTree.insert(i);

        System.out.println(bTree.root.curCount);

        bTree.delete(3);
        System.out.println(bTree.root.curCount);
    }
    public static class Node
    {
        int[] keys;
        Node[] childs;
        int max;
        int curCount;
        boolean isLeaf;

        public Node(int t)
        {
            this.keys = new int[2*t-1];
            this.childs = new Node[2*t];
            this.max = 2*t-1;
            this.curCount = 0;
            this.isLeaf = true;
        }
    }

    public static class BTree
    {
        public int t;
        Node root;
        BTree(int t)
        {
            this.t = t;
            this.root = new Node(t);
        }

        void insert(int k)
        {
            //First check if root is full

            if(root.curCount == 2*t-1)
            {
                Node newRoot = new Node(t);
                newRoot.childs[0] = root;
                newRoot.isLeaf = false;
                split(newRoot,root,0);

                root = newRoot;
            }
            insertNonFullNode(root,k);
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
                    split(root,root.childs[i+1],i+1);
                    if (root.keys[i+1]<k)
                        i++;
                }
                insertNonFullNode(root.childs[i+1],k);
            }
        }

        void split(Node cur, Node childFullNode, int ind)
        {
            Node newNode = new Node(t);
            newNode.isLeaf = childFullNode.isLeaf;
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
            childFullNode.curCount = t-1;
            newNode.curCount = t-1;

            for(int i = ind+1;i<cur.childs.length-1;i++)
                cur.childs[i+1] = cur.childs[i];
            cur.childs[ind+1] = newNode;

            for(int i = ind;i<cur.keys.length-1;i++)
                cur.keys[i+1] = cur.keys[i];

            cur.keys[ind] = childFullNode.keys[t-1];
            cur.curCount+=1;
        }

        void delete(int k)
        {
            delete(root,k);
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
                    root.childs[i] = root.childs[i+1];
                }
            }
            else
            {
                int i = 0;
                while(i<root.keys.length && root.keys[i]<k)
                    i++;
                if(i<root.keys.length && root.keys[i] == k)
                {
                    if(root.childs[i].curCount>t-1)
                    {
                        root.keys[i] = findPredecessor(root.childs[i]);
                        delete(root.childs[i],root.keys[i]);
                    }
                    else if(i+1<root.curCount &&  root.childs[i+1].curCount>t-1)
                    {
                        root.keys[i] = findSuccessor(root.childs[i+1]);
                        delete(root.childs[i+1],root.keys[i]);
                    }
                    else
                    {
                        merge(root,root.childs[i],root.childs[i+1],i);
                        delete(root.childs[i],k);
                    }
                }
            }
        }

        public int findPredecessor(Node child)
        {
            while (!child.isLeaf)
                child = child.childs[child.curCount];
            return child.keys[child.curCount-1];
        }
        public int findSuccessor(Node child)
        {
            while (!child.isLeaf)
                child = child.childs[0];
            return child.keys[0];
        }

        public void merge(Node root, Node child1, Node child2, int indRoot)
        {
            child1.keys[child1.curCount] = root.keys[indRoot];
            for(int i = child1.curCount+1,j = 0;i<2*t-1;i++,j++)
                child1.keys[i] = child2.keys[j];
            for(int i = child1.curCount;i<2*t-1;i++)
                child1.childs[i+1] = child2.childs[i];

            for(int i = indRoot;i<root.curCount-1;i++)
                root.keys[i] = root.keys[i+1];
            for(int i = indRoot+1;i<root.curCount;i++)
                root.childs[i] = root.childs[i+1];

            root.curCount-=1;
            child1.curCount = 2*t-1;

        }
    }

}
