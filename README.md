<h1>Audio Steganography using Fast Fourier Transform.</h1>
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

<b>Output</b>
