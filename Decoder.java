import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Decoder {
    private FileInputStream inputStream;
    private final BinaryTree binaryTree = new BinaryTree();
    private int cut, stream = -1, leafNo = 0, maxLeafNo;
    private boolean moreToRead = true;
    private String dataString = new String();

    public Decoder(File file) throws IOException{
        inputStream = new FileInputStream(file);
        BinaryTree tempTree = binaryTree;
        String fileExtension = new String();
        IOException error = new IOException("Plik ma niepoprawny format.");

        cut = readToInt();

        maxLeafNo = readToInt() + 1;

        stream = readToInt();

        while (stream != 47) {
            fileExtension += (char) stream;
            stream = readToInt();
        }

        recursion(binaryTree);

        File out = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length()-5) + fileExtension);
        out.createNewFile();

        FileOutputStream outputStream= new FileOutputStream(out);

        while (dataString.length()!=0) {
            if (moreToRead && dataString.length()<=7) {
                readToDataString();
                continue;
            }
            if (tempTree.hasValue()) {
                outputStream.write(tempTree.getValue());
                tempTree = binaryTree;
            }

            if (dataString.charAt(0) == '0') {
                tempTree = tempTree.getTree(false);
            } else {
                tempTree = tempTree.getTree(true);
            }

            dataString = dataString.substring(1);
        }
        if (tempTree.hasValue()) {
            outputStream.write(tempTree.getValue());
        }

        System.out.println("Zakończono powodzeniem");
    }
    private int readToInt() throws IOException {
        int stream = -1;
        if ((stream = inputStream.read())==-1) {
            throw new IOException("Plik ma niepoprawny format.");
        }
        return stream;
    }

    private void readToDataString() throws IOException {
        if ((stream = inputStream.read()) != -1) {
            dataString += String.format("%8s", Integer.toBinaryString(stream)).replace(' ', '0');
        } else {
            moreToRead = false;
            dataString = dataString.substring(0, dataString.length() - cut);
        }
    }

    private void recursion(BinaryTree tree) throws IOException{
        if (maxLeafNo==leafNo) {
            return;
        }
        while (dataString.length()<9) {
            readToDataString();
        }
        if (dataString.charAt(0)=='0') {
            dataString = dataString.substring(1);
            recursion(tree.addLeaf(false));
            recursion(tree.addLeaf(true));
        } else {
            leafNo += 1;
            tree.setValue(Integer.parseInt(dataString.substring(1, 9), 2));
            dataString = dataString.substring(9);
        }
    }
}
