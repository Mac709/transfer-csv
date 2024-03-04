import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//https://qiita.com/j-work/items/8f353db0d9bce5ff3b2a
//大文字を小文字にする

public class SagawaCSVTransfer {
    public static void main (String[] args) throws IOException {
        String line;
        // ファイルパス
        Path path1 = Paths.get("trade.csv");
        Path path2 = Paths.get("store.csv");
        // ファイルの行数
        long lineCount1 = Files.lines(path1).count();
        long lineCount2 = Files.lines(path2).count();

        String[][] dataTrade = new String[(int) lineCount1][9];

        int i = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("trade.csv"), "UTF-8"))) {
            while ((line = br.readLine()) != null) {
                dataTrade[i] = line.split(",");
                i++;
            }
        } catch (IOException e) {
            System.out.print("ファイルが存在しませんでした。ファイルを準備してください。");
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("配列の範囲外にアクセスしようとしました。");
        }

        String[][] dataStore = new String[(int) lineCount2][9];
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("store.csv"), "UTF-8"))) {
            int a=0;
            while ((line = br.readLine()) != null) {
                dataStore[a] = line.split(",");
                a++;
            }
        } catch (IOException e) {
            System.out.print("ファイルが存在しませんでした。ファイルを準備してください。");
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("配列の範囲外にアクセスしようとしました。");
        }

        try (PrintWriter pw = new PrintWriter(
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream("sagawa.csv"), "Shift-JIS")))) {

            //取引情報の数確認
            int num1 = 2;
            while (dataTrade[num1].length > 4) {
                num1++;
            }

            TradeAddress ad1 = new TradeAddress();
            String[] address1 = ad1.Address(dataTrade, lineCount1, num1);
            String[] tradeAddress = new String[(int)lineCount1];
            String[] tradeLatterAddress = new String[(int)lineCount1];

            //取引先住所をsplitする
            int num2=0;
            for(int num3=2; num3<num1; num3++){
                 tradeAddress[num2] = dataTrade[num3][5].replace(address1[num2],"");
                 if(tradeAddress[num2].length()>15){
                     tradeLatterAddress[num2] = tradeAddress[num2].replaceFirst(".{10}$", "");
                     tradeAddress[num2] = tradeAddress[num2].replace(tradeLatterAddress[num2],"");
                 }else{
                     tradeLatterAddress[num2] = tradeAddress[num2];
                     tradeAddress[num2] = " ";
                 }
                num2++;
            }

            //送り主住所をsplitする
            StoreAddress ad2 = new StoreAddress();
            String[] address2 = ad2.Address(dataStore, lineCount2);
            String[] storeAddress = new String[(int)lineCount2];
            String[] storeLatterAddress = new String[(int)lineCount2];
            int num5=0;
            for(int num3=2; num3<(int)lineCount2; num3++){
                storeAddress[num5] = dataStore[num3][10].replace(address2[num5],"");
                if(storeAddress[num5].length()>15){
                    storeLatterAddress[num5] = storeAddress[num5].replaceFirst(".{10}$", "");
                    storeAddress[num5] = storeAddress[num5].replace(storeLatterAddress[num5],"");
                }else{
                    storeLatterAddress[num5] = storeAddress[num5];
                    storeAddress[num5] = " ";
                }
                num5++;
            }

            //正規化品名
            String[] replacedDataTrade = new String[num1];
            int num7 = 2;
            for (int n=0; n<num1; n++) {
                if(dataTrade[num7].length > 4) {
                    replacedDataTrade[n] = dataTrade[num7][7].replaceAll("[0-9]:", "");
                    num7++;
                }
            }

            int num6=0;
            for (int a = 2; a < num1; a++) {
                int num4 = 0;
                for (int b = 2; b < lineCount2; b++) {
                    if (dataTrade[a][1].equals(dataStore[b][7])) {
                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(dataTrade[a][6]); //お届け電話番号
                        pw.print(",");

                        pw.print(dataTrade[a][4]); //お届け郵便番号
                        pw.print(",");

                        pw.print(address1[num6]);//お届け住所
                        pw.print(",");

                        pw.print(tradeLatterAddress[num6]);
                        pw.print(",");

                        pw.print(tradeAddress[num6]);
                        pw.print(",");

                        pw.print(dataTrade[a][3]);//お届け名
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print("");//荷送り電話
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(dataStore[b][11]);//依頼主電話
                        pw.print(",");

                        pw.print(dataStore[b][9]);//依頼主郵便
                        pw.print(",");

                        pw.print(address2[num4]);//依頼主住所
                        pw.print(",");

                        pw.print(storeLatterAddress[num4]);
                        pw.print(",");

                        pw.print(storeAddress[num4]);//依頼主名
                        pw.print(",");

                        pw.print(dataStore[b][8]);
                        pw.print(",");

                        pw.print("008");
                        pw.print(",");

                        pw.print(replacedDataTrade[num6]);// 品名
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("1");
                        pw.print(",");

                        pw.print("000");//スピード便
                        pw.print(",");

                        pw.print(" "); //クール便
                        pw.print(",");

                        pw.print(" ");//配達日
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print("");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");

                        pw.print(" ");
                        pw.print(",");
                        pw.println();
                    }
                    num4++;
                }
                num6++;
            }
            pw.close();
            System.out.println("CSVファイルをSagawa用に変換しました。");
        } catch (IOException e) {
            System.out.println(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("data配列の範囲外にアクセスしようとしました。");
        } catch (NullPointerException e) {
            System.out.println("nullの範囲外にアクセスしようとしました。");
        }
    }
}
