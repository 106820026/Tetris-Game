package dao;

import dto.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Record {

    // 讀取紀錄
    public List<Player> loadData(){
        List<Player> players = new ArrayList<Player>();
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/record.dat"));
            players = (List<Player>)ois.readObject();
            ois.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return players;
    }

    // 存檔
    public void setData(Player player){
        // 讀取原始紀錄
        List<Player> players = loadData();
        // 將本局玩家紀錄儲存
        players.add(player);
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/record.dat"));
            oos.writeObject(players);
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
