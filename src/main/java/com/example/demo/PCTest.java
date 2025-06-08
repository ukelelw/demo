package com.example.demo;

//生产者消费者问题
public class PCTest {

    public static void main(String[] args) {
        //创建一个容器
        Container container = new Container();

        //两个线程模拟生产者和消费者，它们操作同一个容器
        new Producer(container).start();
        new Consumer(container).start();
    }

}

//生产者
class Producer extends Thread {
    Container container;

    public Producer(Container container) {
        this.container = container;
    }

    //生产
    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            //模拟生产延时
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //放入缓冲区
            System.out.println("生产了第" + container.push(new Product(i)) + "个产品");
        }
    }
}

//消费者
class Consumer extends Thread {
    Container container;

    public Consumer(Container container) {
        this.container = container;
    }

    //消费
    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            //模拟消费延时
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("消费了第" + container.pop().id + "个产品-->");
        }
    }
}

//产品
class Product {
    int id; //产品编号

    public Product(int id) {
        this.id = id;
    }
}

//缓冲区
class Container {
    //容器大小
    Product[] products = new Product[10];

    //容器计数器 记录数组下标
    int count = -1;

    //生产者放入产品
    public synchronized int push(Product product) {
        //如果容器满了 等待消费者消费
        if (count == (products.length - 1)) {
            //生产者等待消费者消费
            try {
                this.wait(); //生产者线程等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //容器没满 继续生产并放入缓冲区
        products[++count] = product;

        //通知消费者消费
        this.notifyAll(); //唤醒消费者线程

        //返回产品编号
        return product.id;
    }


    //消费者消费产品
    public synchronized Product pop() {
        //判断能否消费
        if (count == -1) { //缓冲区中没有 不能消费
            //消费者等待生产者生产
            try {
                this.wait(); //消费者线程等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //缓冲区中可以消费
        Product product = products[count--];

        //通知生产者生产
        this.notifyAll(); //唤醒生产者线程

        //返回消费产品
        return product;
    }

}


