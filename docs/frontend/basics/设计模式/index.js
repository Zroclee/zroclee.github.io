/**
 * 设计模式
 */

/**
 * 观察者模式
 * 适合一对多关系，一个值的变动引起多个对象的变更
 */
class Observer {
  constructor(id) {
    this.id = id;
  }
  update(data) {
    console.log("更新数据", data);
    const element = document.getElementById(this.id);
    if (element) {
      element.textContent = "观察者" + this.id + "状态：" + String(data);
    }
  }
}
class Subject {
  constructor() {
    this.observers = new Set();
  }

  subscribe(observer) {
    this.observers.add(observer);
  }

  unsubscribe(observer) {
    this.observers.delete(observer);
  }

  notify(data) {
    this.observers.forEach((observer) => observer.update(data));
  }
}

// 观察者实例
const subject = new Subject();
const observer = new Observer("observer-state1");
const observer2 = new Observer("observer-state2");

subject.subscribe(observer);
subject.subscribe(observer2);

function observerUpdateState() {
  console.log("更新状态");
  const input = document.getElementById("state-input");
  const state = (input && input.value) || "0";
  subject.notify(state);
}

/**
 * 发布-订阅模式
 */

// 事件通道
class EventEmitter {
  constructor() {
    this.events = {};
  }
}