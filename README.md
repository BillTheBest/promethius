# Ripley

Ripley is an easy to use monitoring dashboard written in Scala. It uses websockets to push real time updates directly to the board.

## Simple widgets

Here is a simple widget that polls JSON from a web endpoint

```scala
 val w = new PollingWidget("http://time.jsontest.com")
 Scheduler.start(w.run)
```


