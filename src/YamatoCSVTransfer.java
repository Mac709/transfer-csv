import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//https://qiita.com/j-work/items/8f353db0d9bce5ff3b2a
//jacksonは再読みこみ=>file=>project structure=>libraries
//store.csvの電話番号修正
//trade.csvの改行チェック

public class YamatoCSVTransfer {
 public static void main(String[] args) throws IOException {
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
       new BufferedWriter(new OutputStreamWriter(new FileOutputStream("yamato.csv"), "Shift-JIS")))) {

     int num1 = 2;
     int countList1 = 0;
     while (dataTrade[num1].length > 4) {
       countList1 = num1 + 1;
       num1++;
     }

     Address ad = new Address();
     String[] address = ad.Address(dataStore, lineCount2);
     String[] numAddress = new String[(int)lineCount2];
     int num2=0;
     for(int num3=2; num3<(int)lineCount2; num3++){
       numAddress[num2]=dataStore[num3][10].replace(address[num2],"");
       num2++;
     }

     for (int a = 2; a < countList1; a++) {
       int num4 = 0;
       for (int b = 2; b < lineCount2; b++) {
         if (dataTrade[a][1].equals(dataStore[b][7])) {
           pw.print(" ");
           pw.print(",");

           pw.print("発払い");
           pw.print(",");

           pw.print(" ");
           pw.print(",");

           pw.print(" ");
           pw.print(",");

           pw.print("2023/02/21");
           pw.print(",");

           pw.print(" ");
           pw.print(",");

           pw.print(" ");
           pw.print(",");

           pw.print(" ");
           pw.print(",");

           pw.print(dataTrade[a][6]);// 電話番号
           pw.print(",");

           pw.print(" ");
           pw.print(",");

           pw.print(dataTrade[a][4]);// 郵便番号
           pw.print(",");

           pw.print(dataTrade[a][5]);// 住所
           pw.print(",");

           pw.print("");
           pw.print(",");

           pw.print("");// 会社名
           pw.print(",");

           pw.print("");
           pw.print(",");

           pw.print(dataTrade[a][3]);// 氏名
           pw.print(",");

           pw.print("");
           pw.print(",");

           pw.print("");
           pw.print(",");

           pw.print("");
           pw.print(",");

           pw.print(dataStore[b][11]);// 依頼者電話番号
           pw.print(",");

           pw.print("");
           pw.print(",");

           pw.print(dataStore[b][9]);// 依頼者郵便番号
           pw.print(",");

           pw.print(address[num4]);// 依頼者住所
           pw.print(",");

           pw.print(numAddress[num4]);
           pw.print(",");

           pw.print(dataStore[b][8]);// 依頼者組織名
           pw.print(",");

           pw.print("");
           pw.print(",");

           pw.print("");
           pw.print(",");

           pw.print(dataTrade[a][7]);
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

           pw.print("");// 依頼者電話番号
           pw.print(",");

           pw.print("");
           pw.print(",");

           pw.print("");
           pw.println();
         }
         num4++;
       }
     }
     pw.close();
     System.out.println("銘柄残高のCSVファイルをYamato用に変換しました。");
   } catch (IOException e) {
     System.out.println("ファイルを正確に読み込むことができませんでした。ファイルを確認してください。");
   } catch (ArrayIndexOutOfBoundsException e) {
     System.out.println("data配列の範囲外にアクセスしようとしました。");
   } catch (NullPointerException e) {
     System.out.println("nullの範囲外にアクセスしようとしました。");
   }
 }
}
