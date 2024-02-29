<h1>Audio Steganography using Fast Fourier Transform.</h1>
<p>The text is inserted in audio's frequency using the Koch method.</p>
<h2>Usage</h2>
<a>In view/Main.kt<color="#6420AA"/a>
<pre>
  <code class="language-kotlin">
     fun new(){
        val orign ="original_wav_file_path"
        mainPresenter.readFile(orign)
        mainPresenter.encode("I hate steganography")
        mainPresenter.decode()
        mainPresenter.writeBytes("new_wav_file_path")
    }
  </code>
</pre>

<p>Output</p>

![alt text](https://github.com/jkestwill/audioSteganographyFFT/blob/master/zxc.png?raw=true)
