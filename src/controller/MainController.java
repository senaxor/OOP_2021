package controller;

import menus.Logger;
import model.animal.Animal;
import model.animal.AnimalEnum;
import model.animal.asisstant.Cat;
import model.animal.defender.Dog;
import model.animal.producer.Bufallo;
import model.animal.producer.Chicken;
import model.animal.producer.Turkey;
import model.animal.producer.producerAnimal;
import model.goods.*;
import model.level.Level;
import view.Timing;

import java.util.ArrayList;
import java.util.Locale;

public class MainController {

    public ControllerGoods goods;
    public ControllerFactory factories;
    public ControllerAnimal animals;
    public AllLevels allLevels;
    public ControllerGadget gadgets;
    public PersonsController personsController;
    Timing timing;
    public Logger logger ;

    public MainController() {
        this.goods = new ControllerGoods();
        this.factories = new ControllerFactory();
        this.animals = new ControllerAnimal();
        this.allLevels = new AllLevels();
        this.gadgets = new ControllerGadget();
        this.personsController = new PersonsController();
        timing = new Timing();
        this.logger=new Logger();
    }


    public void turn(int n) {
        this.setTimeOfProducing();
        for (int i = 1; i <= n; i++) {
            timing.goForward();
            movingAllAnimal();
            eatAllanimal();
        }
        wellFulling();
        producing();
        decreaseLive();
        showAfterTurn();
    }

    public void setTimeOfProducing(){
        for (Chicken chicken : this.animals.chickens) {
            chicken.produce();
            chicken.decreaseLive();
        }
        for (Bufallo bufallo : this.animals.bufallos) {
            bufallo.produce();
            bufallo.decreaseLive();
        }
        for (Turkey turkey : this.animals.turkeys) {
            turkey.produce();
            turkey.decreaseLive();
        }
    }

    public void showAfterTurn() {
        this.showGood();
        System.out.println("The time passed :" + timing.getCurrentTime());
        goods.showGrass();
        animals.showAnimal();
        showTask(personsController.getCurrentUser().currentLevel);
    }

    public void showTask(Level level){
        for (GoodsEnum value : GoodsEnum.values()) {
            String good = value.toString();
            if(good.equalsIgnoreCase(level.task1)){
                System.out.println("1 : "+level.task1+" : "+returnArrByGoodName(good).size()+"/"+level.task1Number);
            }
        }
        for (AnimalEnum value : AnimalEnum.values()) {
            String animal = value.toString();
            if(animal.equalsIgnoreCase(level.task1)){
                System.out.println("1 : "+level.task1+" : "+returnArrByAnimalName(animal).size()+"/"+level.task1Number);
            }
        }
        if(level.task1.equalsIgnoreCase("coin")){
            System.out.println("coins : "+personsController.getCurrentUser().totalCoins+"/"+level.task1Number);
        }

        for (GoodsEnum value : GoodsEnum.values()) {
            String good = value.toString();
            if(good.equalsIgnoreCase(level.task2)){
                System.out.println("2 : "+level.task2+" : "+returnArrByGoodName(good).size()+"/"+level.task2Number);
            }
        }
        for (AnimalEnum value : AnimalEnum.values()) {
            String animal = value.toString();
            if(animal.equalsIgnoreCase(level.task2)){
                System.out.println("2 : "+level.task2+" : "+returnArrByAnimalName(animal).size()+"/"+level.task2Number);
            }
        }
        if(level.task2.equalsIgnoreCase("coin")){
            System.out.println("coins : "+personsController.getCurrentUser().totalCoins+"/"+level.task2Number);
        }

        for (GoodsEnum value : GoodsEnum.values()) {
            String good = value.toString();
            if(good.equalsIgnoreCase(level.task3)){
                System.out.println("3 : "+level.task3+" : "+returnArrByGoodName(good).size()+"/"+level.task3Number);
            }
        }
        for (AnimalEnum value : AnimalEnum.values()) {
            String animal = value.toString();
            if(animal.equalsIgnoreCase(level.task3)){
                System.out.println("3 : "+level.task3+" : "+returnArrByAnimalName(animal).size()+"/"+level.task3Number);
            }
        }
        if(level.task3.equalsIgnoreCase("coin")){
            System.out.println("coins : "+personsController.getCurrentUser().totalCoins+"/"+level.task3Number);
        }
    }

    public void showGood(){
        if(this.goods.productGoods!=null)
        for (Goods productGood : this.goods.productGoods) {
            System.out.println(productGood.name+"\t x :"+productGood.x+"\t y: "+ productGood.y);
        }
    }

    //Search methods :
    public ArrayList returnArrByGoodName(String GoodName){
        switch (GoodName.toUpperCase(Locale.ROOT)){
            case "EGG" : return gadgets.warehouse.eggInWareHouse;
            case "COOKIE":return gadgets.warehouse.cookieInWareHouse;
            case "SILK":return gadgets.warehouse.silkInWareHouse;
            case "MILK":return gadgets.warehouse.milkInWareHouse;
            case "SEPRATEDMILK":return gadgets.warehouse.sepratedMilkInWareHouse;
            case"FEATHER":return gadgets.warehouse.featherInWareHouse;
            case "CLOTH" :return gadgets.warehouse.clothInWareHouse;
            case "FLOUR" :return gadgets.warehouse.flourInWareHouse;
            case "ICECREAM" :return gadgets.warehouse.iceCreamInWareHouse;
            case "BEARDOLL" :return gadgets.warehouse.bearDollInWareHouse;
            case "LIONDOLL" :return gadgets.warehouse.lionDollInWareHouse;
            case "TIGERDOLL" :return gadgets.warehouse.tigerDollInWareHouse;
        }
        return null;
    }

    public ArrayList returnArrByAnimalName(String animalName){
        switch (animalName){
            case "BUFALLO" :return animals.bufallos;
            case "CHICKEN" :return animals.chickens;
            case "TURKEY" :return animals.turkeys;
            case "BEAR" :return animals.bears;
            case "LION" :return animals.lions;
            case "TIGER" :return animals.tigers;
            case "CAT" :return animals.cats;
            case "DOG" :return animals.dogs;
        }
        return null;
    }

    public Goods returnGoodInWarehouseByName(String name){
        for (Goods goods1 : gadgets.warehouse.existence) {
            if(goods1.name.equalsIgnoreCase(name)){
                return goods1;
            }
        }
        return null;
    }

    public Goods returnProductByLocation(double x , double y){
        for (Goods productGood : goods.productGoods) {
            if(productGood.x==x&&productGood.y==y){
                return productGood;
            }
        }
        return null;
    }

    public Goods returnInTruckGoodsByName(String name){
        for (Goods truckgood : gadgets.truckgoods) {
            if(truckgood.name.equalsIgnoreCase(name))
                return truckgood;
        }
        return null;
    }

    //check time passing after turn :
    public boolean wellFulling(){
        if(gadgets.well.fulling==null)return false;
        if(gadgets.well.fulling.getDate()+gadgets.well.timePeride<=Timing.getCurrentTime()){
            gadgets.well.capacity=5;
            return true;
        }
            return false;
    }

    public void producing(){
        for (Chicken chicken : animals.chickens) {
            if(chicken.produce!=null){
                if(chicken.produce.getDate()+chicken.produceTime<=Timing.getCurrentTime()){
                    goods.productGoods.add(new Egg(chicken.x, chicken.y));
                    chicken.produce=null;
                }
            }
        }
        for (Turkey turkey : animals.turkeys) {
            if (turkey.produce!=null) {
                if (turkey.produce.getDate() + turkey.produceTime <= Timing.getCurrentTime()) {
                    goods.productGoods.add(new Feather(turkey.x, turkey.y));
                    turkey.produce=null;
                }
            }
        }
        for (Bufallo bufallo : animals.bufallos) {
            if (bufallo.produce != null) {
                if (bufallo.produce.getDate() + bufallo.produceTime <= Timing.getCurrentTime()) {
                    goods.productGoods.add(new Milk(bufallo.x, bufallo.y));
                    bufallo.produce = null;
                }
            }
        }
    }

        //Moving methods :
    public boolean movingProducerAnimal(producerAnimal animal) {
        double path;
        double xDest = animal.x, yDest = animal.y;
        double minPath = 20;
        for (Grass grass : goods.grasses) {
            path = Math.abs(animal.x - grass.x) + Math.abs(animal.y - grass.y);
            if (path < minPath) {
                minPath = path;
                xDest = grass.x;
                yDest = grass.y;
            }
        }
        return goToDest(xDest,yDest,animal);
    }

    public boolean goToDest(double xDest , double yDest , Animal animal){
        if (animal.x == xDest && animal.y == yDest) {
        } else if (animal.x == xDest) {
            if (animal.y > yDest) {
                animal.y--;
            } else {
                animal.y++;
                return true;
            }
        } else if (animal.y == yDest) {
            if (animal.x > yDest) {
                animal.x--;
            } else {
                animal.x++;
                return true;
            }
        } else {
            if (Math.abs(animal.x - xDest) > Math.abs(animal.y - yDest)) {
                if (animal.x > yDest) {
                    animal.x--;
                } else {
                    animal.x++;
                    return true;
                }
            } else {
                if (animal.y > yDest) {
                    animal.y--;
                    return true;
                } else {
                    animal.y++;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean movingCatAnimal(Animal animal){
        double path;
        double xDest = 0, yDest = 0;
        double minPath = 20;
        for (Goods productGood : goods.productGoods) {
            path = Math.abs(animal.x - productGood.x) + Math.abs(animal.y - productGood.y);
            if (path < minPath) {
                minPath = path;
                xDest = productGood.x;
                yDest = productGood.y;
            }
        }
        return goToDest(xDest,yDest,animal);
    }

    public void movingAllAnimal(){
        for (Chicken chicken : animals.chickens) {
            movingProducerAnimal(chicken);
        }
        for (Turkey turkey : animals.turkeys) {
            movingProducerAnimal(turkey);
        }
        for (Bufallo bufallo : animals.bufallos) {
            movingProducerAnimal(bufallo);
        }
        for (Cat cat : animals.cats) {
            movingCatAnimal(cat);
        }
        //TODO Dogs , Wilds
    }
        //Eating methods :
    public boolean eat(producerAnimal animal){
        for (Grass grass : goods.grasses) {
            if(grass.x==animal.x&&grass.y==animal.y){
                goods.grasses.remove(grass);
                animal.livies=110;
                return true;
            }
        }
        return false;
    }

    public void eatAllanimal(){
        for (Chicken chicken : animals.chickens) {
            eat(chicken);
        }
        for (Bufallo bufallo : animals.bufallos) {
            eat(bufallo);
        }
        for (Turkey turkey : animals.turkeys) {
            eat(turkey);
        }
    }

    
    public void decreaseLive(){
        boolean bool;
        ArrayList<Bufallo> index1  = new ArrayList<>();
        for (Bufallo bufallo : animals.bufallos) {
           if(bufallo.decreaseLive!=null){
               if(bufallo.decreaseLive.getDate()+1<=Timing.getCurrentTime()){
                   bufallo.livies-=10*(Timing.getCurrentTime()-bufallo.decreaseLive.getDate());
                   if(bufallo.isDie()) index1.add(bufallo);
               }
           }
        }
        for (Bufallo bufallo : index1) {
            bool=false;
            for (Bufallo bufallo1 : animals.bufallos) {
                if(bufallo.equals(bufallo1)) bool=true;
            }
            if(bool)
            animals.bufallos.remove(bufallo);
        }
        index1.clear();
        ArrayList<Chicken> index  = new ArrayList<>();
        for (Chicken chicken : animals.chickens) {
            if(chicken.decreaseLive!=null){
                if(chicken.decreaseLive.getDate()+1<=Timing.getCurrentTime()){
                    chicken.livies-=10*(Timing.getCurrentTime()-chicken.decreaseLive.getDate());
                    if(chicken.isDie())
                        index.add(chicken);
                }
            }
        }
        for (Chicken chicken : index) {
            bool =false;
            for (Chicken chicken1 : animals.chickens) {
                if(chicken.equals(chicken1)) bool = true;
            }
            if(bool)
            animals.chickens.remove(chicken);
        }
        index.clear();
        ArrayList<Turkey> index2 = new ArrayList<>();
        for (Turkey turkey : animals.turkeys) {
            if(turkey.decreaseLive!=null){
                if(turkey.decreaseLive.getDate()+1<=Timing.getCurrentTime()){
                    turkey.livies-=10*(Timing.getCurrentTime()-turkey.decreaseLive.getDate());
                    if(turkey.isDie()) index2.add(turkey);
                }
            }
        }
        for (Turkey turkey : index2) {
            bool=false;
            for (Turkey turkey1 : animals.turkeys) {
                if(turkey.equals(turkey1)) bool = true;
            }
            if(bool) animals.turkeys.remove(turkey);
        }
        index2.clear();
    }
}
