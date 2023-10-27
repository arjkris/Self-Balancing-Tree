public class btreecode {
    public static void main(String[] args) {
        BTree bTree = new BTree(3);
        bTree.insert(10);
        bTree.insert(20);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(12);
        bTree.insert(30);
        bTree.insert(7);
        bTree.insert(17);

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
                    if (root.keys[i+1]<1)
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


    }

}
