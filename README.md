# Ripley

Ripley is an easy to use monitoring dashboard written in Scala. It uses websockets to push real time updates directly to the board.

The idea is to give you "just enough" to build great looking real-time dashboards while allowing you the freedom to customize things easily.

![](https://raw.githubusercontent.com/owainlewis/ripley/master/public/images/preview.png)

## Concept

Ripley is designed to be simple and easy to understand. There are two main concepts that you'll need to understand when creating widgets.

1. A global web-socket channel is exposed in Scala code to which you can push data to. You push a "key" and a "value" to the channel. I.e

```scala

import backend.SocketChannel

// Send a message to the channel. **Note that the value must be a string**

SocketChannel.push("greeting", "OH HAI")

```

When an event is pushed to the channel the JavaScript front end looks for the key and a HTML entity with a data-key value set. If it finds one the front end HTML gets updated in real time.


```html
<div data-key="random-number" class="widget red">
  <p>The value of my number is</p>
  <div class="value"></div>
</div>

```

2. A scheduler is provided for running tasks at specific intervals.

The scheduler takes a single function with no parameters and a polling interval in seconds.

Let's take a look at the simplest possible widget which pushes a Random integer to the front end every 10 seconds. We will start with the Scala code.

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

## Simple widgets

Here is a simple widget that polls JSON from a web endpoint

```scala
 val w = new PollingWidget("http://time.jsontest.com")
 Scheduler.start(w.run)
```


