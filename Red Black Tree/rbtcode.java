
public class rbtcode {
    public static rbtcode.Node Root;
    public static void main(String[] args) {
        Root = null;
        rbtcode obj = new rbtcode();
        Root = obj.Insert(Root,10);
        Root = obj.Insert(Root,18);
        Root = obj.Insert(Root,7);
        Root = obj.Insert(Root,15);
        Root = obj.Insert(Root,16);
        Root = obj.Insert(Root,30);
        Root = obj.Insert(Root,25);
        Root = obj.Insert(Root,40);
        Root = obj.Insert(Root,60);
        Root = obj.Insert(Root,2);
        Root = obj.Insert(Root,1);
        Root = obj.Insert(Root,70);
        System.out.println(Root.val);
    }

    public class Node{
        int val;
        char color;
        Node left;
        Node right;

        public Node(int val)
        {
            this.val = val;
            this.color = 'R';
            this.left = null;
            this.right = null;
        }
    }

    public char getColor(Node root)
    {
        if(root == null)
            return 'B';
        return root.color;
    }
    public Node colorBothChildBlackAndParentRed(Node root)
    {
        root.left.color = 'B';
        root.right.color = 'B';
        if(root != Root)
        {
            root.color = 'R';
        }
        return root;
    }
    public Node LLrotation(Node root)
    {
        Node newroot = root.left;
        newroot.color = 'B';
        root.color = 'R';
        root.left = newroot.right;
        newroot.right = root;
        return newroot;
    }
    public Node LRrotation(Node root)
    {
        Node newroot = root.left.right;
        root.left.right = newroot.left;
        newroot.left = root.left;
        root.left = newroot.right;
        newroot.right = root;
        newroot.color = 'B';
        root.color = 'R';
        return newroot;
    }
    public Node RRrotation(Node root)
    {
        Node newroot = root.right;
        newroot.color = 'B';
        root.color = 'R';
        root.right = newroot.left;
        newroot.left = root;
        return newroot;
    }
    public Node RLrotation(Node root)
    {
        Node newroot = root.right.left;
        root.right.left = newroot.right;
        newroot.right = root.right;
        root.right = newroot.left;
        newroot.left = root;
        newroot.color = 'B';
        root.color = 'R';
        return newroot;
    }

    public Node Insert(Node root,int val)
    {
        if(root == null)
        {
            Node r = new Node(val);
            if(Root == null)
                r.color = 'B';
            return r;
        }
        if(val < root.val)
            root.left = Insert(root.left,val);
        else if(root.val < val)
            root.right = Insert(root.right,val);

        if(getColor(root.left) == 'R' && getColor(root.left.left) == 'R')
        {
            if(getColor(root.right) == 'R')
            {
                return colorBothChildBlackAndParentRed(root);
            }
            else
            {
                return LLrotation(root);
            }
        }
        else if(getColor(root.left) == 'R' && getColor(root.left.right) == 'R')
        {
            if(getColor(root.right) == 'R')
            {
                return colorBothChildBlackAndParentRed(root);
            }
            else
            {
                return LRrotation(root);
            }
        }
        else if(getColor(root.right) == 'R' && getColor(root.right.right) == 'R')
        {
            if(getColor(root.left) == 'R')
            {
                return colorBothChildBlackAndParentRed(root);
            }
            else
            {
                return RRrotation(root);
            }
        }
        else if(getColor(root.right) == 'R' && getColor(root.right.left) == 'R')
        {
            if(getColor(root.left) == 'R')
            {
                return colorBothChildBlackAndParentRed(root);
            }
            else
            {
                return RLrotation(root);
            }
        }
        return root;
    }
}