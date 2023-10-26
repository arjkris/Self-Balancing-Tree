
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

        //Delete
        obj.delete(Root,25);
        obj.delete(Root,15);
        obj.delete(Root,16);
        System.out.println(Root.val);
    }

    public class Node{
        int val;
        char color;
        Node left;
        Node right;
        Node parent;

        public Node(int val)
        {
            this.val = val;
            this.color = 'R';
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        public Node getSiblingNode()
        {
            if(parent != null)
            {
                if(parent.right == this)
                    return parent.left;
                return parent.right;
            }
            return null;
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
        Node rootsParent = root.parent;

        root.left.right = newroot.left;
        newroot.left = root.left;
        root.left = newroot.right;
        newroot.right = root;

        //Parent
//        root.left.right.parent = root.left;
//        root.left.parent = newroot;
//        newroot.parent = rootsParent;
//        root.parent = newroot;
//        root.left.parent = root;

        newroot.color = 'B';
        root.color = 'R';
        return newroot;
    }
    public Node RRrotation(Node root)
    {
        Node newroot = root.right;
        //Node rootsParent = root.parent;
        newroot.color = 'B';
        root.color = 'R';
        root.right = newroot.left;
        newroot.left = root;
        return newroot;
    }
    public Node RLrotation(Node root)
    {
        Node newroot = root.right.left;
        Node rootsParent = root.parent;

        root.right.left = newroot.right;
        newroot.right = root.right;
        root.right = newroot.left;
        newroot.left = root;

        //Parent
//        root.right.left.parent = root.right;
//        root.right.parent = newroot;
//        newroot.parent = rootsParent;
//        root.parent = newroot;
//        root.right.parent = root;
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
        if(root.right!=null)
            root.right.parent = root;
        if(root.left != null)
            root.left.parent = root;

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

    public void delete(Node root, int val)
    {
        if(root == null)
            return;
        if(val < root.val)
        {
            delete(root.left,val);
        }
        else if(root.val < val)
        {
            delete(root.right,val);
        }
        else
        {
            if(root.left == null && root.right == null)
            {
                Node delNode = root;
                if(root ==  Root)
                {
                    Root = null;
                    return;
                }
                if(root.color == 'B')
                {
                    fixDoubleBlack(root);
                }
                if(delNode.parent.left == root)
                {
                    root.parent.left = null;
                }
                else
                    root.parent.right = null;

            }
            else if(root.left == null || root.right == null)
            {
                if(root.right == null)
                {
                    Node temp = getInOrderPre(root.left);
                    root.val = temp.val;
                    delete(root.left,temp.val);
                }
                else
                {
                    Node temp = getInOrderSuc(root.right);
                    root.val = temp.val;
                    delete(root.right,temp.val);
                }
            }
            else
            {
                Node temp = getInOrderPre(root.left);
                root.val = temp.val;
                delete(root.left,temp.val);
            }
        }
    }
    public void fixDoubleBlack(Node root)
    {
        if(root == Root)
            return;
        Node siblingNode = root.getSiblingNode();
        if(siblingNode == null)
            fixDoubleBlack(root.parent);
        else
        {
            if(siblingNode.color == 'R')
            {
                root.parent.color = 'R';
                siblingNode.color = 'B';
                //Rotate Left
                if(root.parent.right == siblingNode)
                {
                    leftRotate(root.parent);
                }
                //Rotate Right
                else
                {
                    rightRotate(root.parent);
                }
                fixDoubleBlack(root);
            }
            else
            {
                Node parent = siblingNode.parent;
                if(getColor(siblingNode.left) == 'R' || getColor(siblingNode.right) == 'R')
                {
                    if(parent.right == siblingNode && getColor(siblingNode.right) == 'R')
                    {
                        siblingNode.right.color = siblingNode.color;
                        siblingNode.color = parent.color;
                        leftRotate(parent);
                    }
                    else if(parent.right == siblingNode && getColor(siblingNode.left) == 'R')
                    {
                        siblingNode.left.color = parent.color;
                        rightRotate(siblingNode);
                        leftRotate(parent);
                    }
                    else if(parent.left == siblingNode && getColor(siblingNode.left) == 'R')
                    {
                        siblingNode.left.color = siblingNode.color;
                        siblingNode.color = parent.color;
                        rightRotate(parent);
                    }
                    else if(parent.left == siblingNode && getColor(siblingNode.right) == 'R')
                    {
                        siblingNode.right.color = parent.color;
                        leftRotate(siblingNode);
                        rightRotate(parent);
                    }
                    parent.color = 'B';
                }
                else
                {
                    siblingNode.color = 'R';
                    if(root.parent.color == 'B')
                        fixDoubleBlack(root.parent);
                    else
                        root.parent.color = 'B';
                }
            }
        }

    }
    public void leftRotate(Node root)
    {
        Node newParent = root.right;
        if(Root == root)
            Root = newParent;
        Node parentsParent = root.parent;
        root.parent = newParent;
        newParent.parent = parentsParent;
        root.right = newParent.left;
        root.right.parent = root;
        if(newParent.parent.left == root)
            newParent.parent.left = newParent;
        else
            newParent.parent.right = newParent;
        newParent.left = root;
    }
    public void rightRotate(Node root)
    {
        Node newParent = root.left;
        if(Root == root)
            Root = newParent;
        Node parentsParent = root.parent;
        root.parent = newParent;
        newParent.parent = parentsParent;
        root.left = newParent.right;
        root.left.parent = root;
        if(newParent.parent.right == root)
            newParent.parent.right = newParent;
        else
            newParent.parent.left = newParent;
        newParent.right = root;
    }
    public Node getInOrderPre(Node root)
    {
        Node cur = root;
        while (cur != null && cur.right!=null)
            cur = cur.right;
        return cur;
    }
    public Node getInOrderSuc(Node root)
    {
        Node cur = root;
        while (cur != null && cur.left!=null)
            cur = cur.left;
        return cur;
    }
}