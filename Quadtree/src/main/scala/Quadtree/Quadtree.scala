package Quadtree

import scalafx.scene.Node
import scalafx.scene.shape.Line

case class Point(val x: Int, val y: Int)

class QuadTree(points: List[Point]) {
  val width = 1280
  val height = 960
  val center = Point(width/2, height/2)
  val max_elems = 1
  class QNode(val width: Int, val height: Int, val center: Point) {
    var nw : Option[QNode] = None
    var ne : Option[QNode] = None
    var sw : Option[QNode] = None
    var se : Option[QNode] = None
    var elems = Seq[Point]()
  }

  val root = makeTree(Point(width/2, height/2), width, height, points)

  def makeTree(center: Point, width: Int, height: Int, points: List[Point]) : QNode = {
    val node = new QNode(width, height, center)
    if(points.size<=max_elems)
      node.elems = points
    else {
      val hwidth = width/2
      val hheight = height/2
      node.nw = Some(makeTree(Point(center.x-hwidth/2, center.y-hheight/2), hwidth, hheight, points.filter(p => contains(p, Point(center.x-hwidth/2, center.y-hheight/2), hwidth, hheight))))
      node.ne = Some(makeTree(Point(center.x+hwidth/2, center.y-hheight/2), hwidth, hheight, points.filter(p => contains(p, Point(center.x+hwidth/2, center.y-hheight/2), hwidth, hheight))))
      node.sw = Some(makeTree(Point(center.x-hwidth/2, center.y+hheight/2), hwidth, hheight, points.filter(p => contains(p, Point(center.x-hwidth/2, center.y+hheight/2), hwidth, hheight))))
      node.se = Some(makeTree(Point(center.x+hwidth/2, center.y+hheight/2), hwidth, hheight, points.filter(p => contains(p, Point(center.x+hwidth/2, center.y+hheight/2), hwidth, hheight))))
    }
    node
  }

  def contains(point: Point, center: Point, width: Int, height: Int): Boolean = (point.x>=center.x-width/2) && (point.x<center.x+width/2) && (point.y>=center.y-height/2) && (point.y<center.y+height/2)

  def draw(node: QNode): Array[Line] = {
    if (node.nw == None)
      Array(
        Line(node.center.x-node.width/2,node.center.y+node.height/2,node.center.x+node.width/2,node.center.y+node.height/2),
        Line(node.center.x-node.width/2,node.center.y-node.height/2,node.center.x+node.width/2,node.center.y-node.height/2),
        Line(node.center.x-node.width/2,node.center.y-node.height/2,node.center.x-node.width/2,node.center.y+node.height/2),
        Line(node.center.x+node.width/2,node.center.y-node.height/2,node.center.x+node.width/2,node.center.y+node.height/2)
      )
    else {
      draw(node.nw.get) ++ draw(node.ne.get) ++ draw(node.sw.get) ++ draw(node.se.get)
    }
  }

  def print(node: QNode): String = {
    if (node.nw == None)
      node.elems.toString
    else
      "(" + print(node.nw.get) + ", " + print(node.ne.get) + ", " + print(node.sw.get) + ", " + print(node.se.get) + ")"
  }
}