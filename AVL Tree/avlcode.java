public class avlcode {
    public static void main(String[] args) {
        Node root = null;
        avlcode obj = new avlcode();
        root = obj.Insert(root,20);
        root = obj.Insert(root,10);
        root = obj.Insert(root,30);
        root = obj.Insert(root,40);
        root = obj.Insert(root,100);
        root = obj.Insert(root,5);
        root = obj.Insert(root,110);
        root = obj.Insert(root,1);
        root = obj.Insert(root,33);
        root = obj.Insert(root,65);
        root = obj.Insert(root,48);
        root = obj.Insert(root,105);
        root = obj.Insert(root,99);
        System.out.println(root.val);

        //Delete
        root = obj.delete(root,105);
        root = obj.delete(root,110);
        System.out.println(root.val);
    }

    public class Node{
        int val;
        int height;
        Node left;
        Node right;

        public Node(int val)
        {
            this.val = val;
            this.height = 0;
            this.left = null;
            this.right = null;
        }
    }

    public int NodeHeight(Node root)
    {
        int l = 0, r = 0;
        if(root.left != null)
            l = root.left.height+1;
        if(root.right != null)
            r = root.right.height+1;
        return Math.max(l,r);
    }

    public int BalanceFactor(Node root)
    {
        int l = 0, r = 0;
        if(root != null && root.left != null)
            l = root.left.height+1;
        if(root != null && root.right != null)
            r = root.right.height+1;
        return l-r;
    }

    public Node LLrotation(Node root)
    {
        Node newroot = root.left;
        root.left = newroot.right;
        newroot.right = root;
        root.height = NodeHeight(root);
        newroot.height = NodeHeight(newroot);
        return newroot;
    }
    public Node RRrotation(Node root)
    {
        Node newroot = root.right;
        root.right = newroot.left;
        newroot.left = root;
        root.height = NodeHeight(root);
        newroot.height = NodeHeight(newroot);
        return newroot;
    }
    public Node LRrotation(Node root)
    {
        Node newroot = root.left.right;
        root.left.right = newroot.left;
        newroot.left = root.left;
        root.left = newroot.right;
        newroot.right = root;
        newroot.left.height = NodeHeight(newroot.left);
        newroot.right.height = NodeHeight(newroot.right);
        newroot.height = NodeHeight(newroot);
        return newroot;
    }
    public Node RLrotation(Node root)
    {
        Node newroot = root.right.left;
        root.right.left = newroot.right;
        newroot.right = root.right;
        root.right = newroot.left;
        newroot.left = root;
        newroot.left.height = NodeHeight(newroot.left);
        newroot.right.height = NodeHeight(newroot.right);
        newroot.height = NodeHeight(newroot);
        return newroot;
    }

    public Node Insert(Node root, int val)
    {
        if(root == null)
        {
            return new Node(val);
        }
        if(root.val>val)
            root.left = Insert(root.left,val);
        else if(root.val<val)
            root.right = Insert(root.right,val);

        root.height = NodeHeight(root);
        if(BalanceFactor(root) == 2 && BalanceFactor(root.left)==1)
        {
            return LLrotation(root);
        }
        else if(BalanceFactor(root) == 2 && BalanceFactor(root.left)==-1)
        {
            return LRrotation(root);
        }
        else if(BalanceFactor(root) == -2 && BalanceFactor(root.right)==1)
        {
            return RLrotation(root);
        }
        else if(BalanceFactor(root) == -2 && BalanceFactor(root.right)==-1)
        {
            return RRrotation(root);
        }
        return root;
    }

    public Node delete(Node root, int val)
    {
        if(root == null)
            return root;
        if(root.val>val)
            root.left = delete(root.left,val);
        else if(root.val<val)
            root.right = delete(root.right,val);
        else
        {
            if(root.right == null || root.left == null)
            {
                Node temp = root.left;
                if(temp == null)
                    temp = root.right;
                if(temp == null)
                    return null;
                root = temp;
            }
            else
            {
                Node temp = getInOrderPre(root.left);
                root.val = temp.val;
                root.left = delete(root.left,temp.val);
            }
        }
        root.height = NodeHeight(root);
        if(BalanceFactor(root) == 2 && BalanceFactor(root.left)==1)
        {
            return LLrotation(root);
        }
        else if(BalanceFactor(root) == 2 && BalanceFactor(root.left)==-1)
        {
            return LRrotation(root);
        }
        else if(BalanceFactor(root) == 2 && BalanceFactor(root.left)==0)
        {
            return LLrotation(root);
        }
        else if(BalanceFactor(root) == -2 && BalanceFactor(root.right)==1)
        {
            return RLrotation(root);
        }
        else if(BalanceFactor(root) == -2 && BalanceFactor(root.right)==-1)
        {
            return RRrotation(root);
        }
        else if(BalanceFactor(root) == -2 && BalanceFactor(root.right)==0)
        {
            return RRrotation(root);
        }

        return root;
    }

    public Node getInOrderPre(Node root)
    {
        Node cur = root;
        while (cur != null && cur.right!=null)
            cur = cur.right;
        return cur;
    }
}
