# Ripley

Ripley is an easy to use monitoring dashboard written in Scala. It uses websockets to push real time updates directly to the board.

The idea is to give you "just enough" to build great looking real-time dashboards while allowing you the freedom to customize things easily.

![](https://raw.githubusercontent.com/owainlewis/ripley/master/public/images/preview.png)

## Concept

Ripley is designed to be simple and easy to understand. There are two main concepts that you'll need to understand when creating widgets.

### 1. Websocket Channel

A global web-socket channel is exposed in Scala code to which you can push data to. You push a "key" and a "value" to the channel. I.e

```scala

import backend.SocketChannel

// Send a message to the channel. **Note that the value must be a string**

SocketChannel.push("greeting", "OH HAI")

```

When an event is pushed to the channel the JavaScript front end looks for the key and a HTML entity with a data-key value set. If it finds one the front end HTML gets updated in real time.


```html
<div data-key="greeting" class="widget red">
  <div class="value"></div> <!-- this will update to OH HAI when hooked up -->
</div>

```

### 2. Scheduler

A scheduler is provided for running tasks at specific intervals.

The scheduler takes a single function with no parameters and a polling interval in seconds.


```scala

import actors.Scheduler

object Global extends GlobalSettings {
  def startApplication() {
    val a = new TimeWidget("time")
    Scheduler.start(a.run, 10) // call the run function every 10 seconds
  }
}

```

## A full example

Let's take a look at the simplest possible widget which pushes a Random integer to the front end every 10 seconds. We will start with the Scala code.

Some example widgets are available in app/widgets to help you get started

```scala
package widgets

import backend.SocketChannel
import scala.util.Random.nextInt

class SimpleWidget(key: String) extends Widget {
  def run() = {
    SocketChannel.push(key, nextInt(1000).toString)
  }
}
```

Now the markup which lives in app/views/index.html

```html
<div data-key="random-number" class="widget blue">
  <div class="value"></div>
</div>
```

And finally let's hook up the widget to a scheduler and run it every 5 seconds. This is done in app/Global.scala

```scala

import actors.Scheduler

object MyApp {

  def start() = {
    val a = new TimeWidget("time")
    Scheduler.start(a.run, 5)
  }
}
```

## Want to help?

Feel free to submit a pull request if you have ideas.


