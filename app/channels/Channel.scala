package channels

trait Channel[T] {

  def run(): T

}
