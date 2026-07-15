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
    // 存储事件名称对应的回调函数集合
    this.events = new Map();
  }

  // 订阅事件
  subscribe(eventName, callback) {
    if (!this.events.has(eventName)) {
      this.events.set(eventName, new Set());
    }
    this.events.get(eventName).add(callback);
  }

  // 取消订阅
  unsubscribe(eventName, callback) {
    if (!this.events.has(eventName)) return;
    if (callback) {
      this.events.get(eventName).delete(callback);
      // 如果该事件已经没有callback，可以删除整个键值对
      if (this.events.get(eventName).size === 0) {
        this.events.delete(eventName);
      }
    } else {
      this.events.delete(eventName);
    }
  }

  // 事件触发
  emit(eventName, ...args) {
    if (!this.events.has(eventName)) return;
    // this.events.get(eventName).forEach((callback) => callback(...args));
    for (const callback of this.events.get(eventName)) {
      try {
        callback(...args);
      } catch (error) {
        console.error("事件触发失败", error);
      }
    }
  }

  // 事件订阅一次
  once(eventName, callback) {
    const wrapper = (...args) => {
      callback(...args);
      this.unsubscribe(eventName, wrapper);
    };
    this.subscribe(eventName, wrapper);
  }
}

const emitter = new EventEmitter();

emitter.subscribe("update", (data) => {
  const element = document.getElementById("publisher-state1");
  if (element) {
    element.textContent = "订阅者1状态：" + String(data);
  }
});
emitter.subscribe("update", (data) => {
  const element = document.getElementById("publisher-state2");
  if (element) {
    element.textContent = "订阅者2状态：" + String(data);
  }
});
emitter.once("update", (data) => {
  const element = document.getElementById("publisher-state3");
  if (element) {
    element.textContent = "订阅一次状态：" + String(data);
  }
});

// 事件触发
function publisherUpdateState() {
  const input = document.getElementById("publisher-state-input");
  const state = (input && input.value) || "0";
  console.log("更新数据", state);
  emitter.emit("update", state);
}

/**
 * 单例模式（Singleton）
 * 适合全局资源管理，确保只有一个实例存在
 */

class Singleton {
  static instance = null;
  constructor() {
    if (Singleton.instance) {
      return Singleton.instance;
    }
    this.data = "default";
    Singleton.instance = this;
  }
  getData() {
    return this.data;
  }
  setData(data) {
    this.data = data;
  }
  getInstance() {
    return Singleton.instance;
  }
}

// 测试
const singleton1 = new Singleton();
const singleton2 = new Singleton();
console.log(singleton1 === singleton2); // true
singleton1.setData("new data");
console.log(singleton2.getData()); // new data
