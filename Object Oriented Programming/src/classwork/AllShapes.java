
package classwork;


public class AllShapes {
    public static void main (String[] args){
        Shape[] shapeObject = {
            new Circle(),
            new Rectangle(),
            new Triangle()
        };
        
        for(Shape shape : shapeObject) {
            shape.draw();
        }
    }
}

