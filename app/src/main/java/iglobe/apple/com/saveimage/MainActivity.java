package iglobe.apple.com.saveimage;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class MainActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the widgets reference from XML layout
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        final Button btn = (Button) findViewById(R.id.btn);
        final ImageView iv_source = (ImageView) findViewById(R.id.iv_source);
        final ImageView iv_saved = (ImageView) findViewById(R.id.iv_saved);
        final TextView tv_saved = (TextView) findViewById(R.id.tv_saved);

        final ActionBar ab = getActionBar();

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                // Get the image from drawable resource as drawable object
                Drawable drawable = getDrawable(R.drawable.messi);

                // Get the bitmap from drawable object
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

                /*
                    File
                        An "abstract" representation of a file system entity identified by a
                        pathname. The pathname may be absolute (relative to the root directory
                        of the file system) or relative to the current directory in which
                        the program is running.

                        The actual file referenced by a File may or may not exist. It may also,
                        despite the name File, be a directory or other non-regular file.
                */

                // Initializing a new file
                File file;

                /*
                    Environment
                        Provides access to environment variables.

                    public static File getExternalStorageDirectory ()
                        Return the primary external storage directory. This directory may not
                        currently be accessible if it has been mounted by the user on their
                        computer, has been removed from the device, or some other problem has
                        happened. You can determine its current state with getExternalStorageState().

                        Note: don't be confused by the word "external" here. This directory can
                        better be thought as media/shared storage. It is a filesystem that can hold
                        a relatively large amount of data and that is shared across all applications
                        (does not enforce permissions). Traditionally this is an SD card, but it may
                        also be implemented as built-in storage in a device that is distinct from the
                        protected internal storage and can be mounted as a filesystem on a computer.
                */

                // Get the external storage directory path
                String path = Environment.getExternalStorageDirectory().toString();

                /*
                    public File (String dirPath, String name)
                        Constructs a new File using the specified directory path and file name,
                        placing a path separator between the two.

                        Parameters
                            dirPath : the path to the directory where the file is stored.
                            name : the file's name.

                        Throws
                            NullPointerException if name == null.
                */

                // Create a file to save the image
                file = new File(path, "UniqueFileName"+".jpg");

                try{
                    /*
                        OutputStream
                            A writable sink for bytes.

                            Most clients will use output streams that write data to the file system
                            (FileOutputStream), the network (getOutputStream()/getOutputStream()),
                            or to an in-memory byte array (ByteArrayOutputStream).

                            Use OutputStreamWriter to adapt a byte stream like this one into a
                            character stream.
                    */
                    OutputStream stream = null;

                    /*
                        FileOutputStream
                            An output stream that writes bytes to a file. If the output file exists,
                            it can be replaced or appended to. If it does not exist, a new
                            file will be created.
                    */

                    /*
                        public FileOutputStream (File file)
                            Constructs a new FileOutputStream that writes to file. The file will be
                            truncated if it exists, and created if it doesn't exist.

                            Throws
                                FileNotFoundException : if file cannot be opened for writing.
                    */

                    stream = new FileOutputStream(file);

                    /*
                        public boolean compress (Bitmap.CompressFormat format, int quality, OutputStream stream)
                            Write a compressed version of the bitmap to the specified outputstream.
                            If this returns true, the bitmap can be reconstructed by passing a
                            corresponding inputstream to BitmapFactory.decodeStream().

                            Note: not all Formats support all bitmap configs directly, so it is
                            possible that the returned bitmap from BitmapFactory could be in a
                            different bitdepth, and/or may have lost per-pixel alpha
                            (e.g. JPEG only supports opaque pixels).

                            Parameters
                                format : The format of the compressed image
                                quality : Hint to the compressor, 0-100. 0 meaning compress for small
                                    size, 100 meaning compress for max quality. Some formats,
                                    like PNG which is lossless, will ignore the quality setting
                                stream : The outputstream to write the compressed data.
                            Returns
                                true : if successfully compressed to the specified stream.
                    */
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

                    /*
                        public void flush ()
                            Flushes this stream. Implementations of this method should ensure
                            that any buffered data is written out. This implementation does nothing.

                            Throws
                                IOException : if an error occurs while flushing this stream.
                    */
                    stream.flush();

                    /*
                        public void close ()
                            Closes this stream. Implementations of this method should free any
                            resources used by the stream. This implementation does nothing.

                            Throws
                                IOException : if an error occurs while closing this stream.
                    */
                    stream.close();

                }catch (IOException e) // Catch the exception
                {
                    e.printStackTrace();
                }

                // Parse the saved image path to uri
                Uri savedImageURI = Uri.parse(file.getAbsolutePath());

                // Display the saved image to ImageView
                iv_saved.setImageURI(savedImageURI);

                // Display saved image uri to TextView
                tv_saved.setText("Image saved in external storage.\n" + savedImageURI);
            }
        });
    }
}
