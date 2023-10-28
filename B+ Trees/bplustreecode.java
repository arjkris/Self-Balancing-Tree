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
    }
}
