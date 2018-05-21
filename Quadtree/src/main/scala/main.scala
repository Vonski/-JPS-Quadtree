import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.input.{MouseEvent, _}
import scalafx.scene.shape._
import Quadtree._

object ScalaFXHelloWorld extends JFXApp {

  stage = new JFXApp.PrimaryStage {
    title = "My Scene"
    scene = new Scene(1280, 960) {
      val rectangle = Rectangle(20,20,200,100)

      val elem = new Point(10,10)
      content = Array(Rectangle(1,1,1,1))

      val rect = Rectangle(4, 4, 4, 4)

      content += rect

      val points = List(Point(1,2), Point(20,2), Point(700,2), Point(1,600), Point(700,600))
      for(point <- points)
        content += Rectangle(point.x, point.y, 4, 4)

      val tree = new QuadTree(points)

      val boxes = tree.draw(tree.root)
      for (line <- boxes)
        content += line

      onMouseMoved = (me: MouseEvent) => {
        rect.x = me.x - 0.5 * rect.width()
        rect.y = me.y - 0.5 * rect.height()
      }

      onMouseClicked = (e: MouseEvent) => {
        e.button match {
          case MouseButton.Primary => content += Rectangle(e.x-2, e.y-2, 4, 4)
          case MouseButton.Secondary => content.remove(2)
        }
      }
    }
  }

  stage.setResizable(false)

}