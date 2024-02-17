# IDM-Internet-Download-Manager

## Console-based Internet Download Manager (IDM) using Java

### Abstract
The Internet Download Manager (IDM) Console Application is a Java-based project designed to provide a simple and efficient solution for managing file downloads from the Internet. This console application incorporates essential features such as multiple file downloads, download resumption, queue management, and basic file organization.

### Key Features:
- **Multiple File Download**: Users can initiate the simultaneous download of various files by adding multiple URLs to the download queue. The application uses multithreading to handle concurrent downloads efficiently.
- **Resume Interrupted Download**: The IDM supports the resumption of interrupted downloads caused by network issues, system shutdowns, or any other disruptions. By utilizing the Range HTTP header, the application requests only the remaining portions of a file, saving bandwidth and time.
- **Queue Management**: Users can prioritize and schedule downloads by adding tasks to the download queue. The program processes the tasks in the order they are added, allowing users to control the timing and order of their downloads.
- **File Organization**: Downloaded files are stored in a specified folder (configurable through the DOWNLOADS_FOLDER constant). This promotes organization and helps users easily locate and manage their downloaded content.

### Introduction
In an era dominated by digital content consumption and information sharing, efficient and organized file downloads from the internet play a pivotal role in enhancing productivity and convenience. The Internet Download Manager (IDM) Console Application emerges as a robust and user-friendly solution to address the evolving needs of individuals seeking a streamlined and customizable approach to managing their downloads.

This project leverages the versatility and portability of Java to provide users with a command-line interface for initiating and overseeing multiple file downloads concurrently. By integrating key features such as the ability to resume interrupted downloads, queue management, and basic file organization, the IDM Console Application aims to enhance the user experience, providing a flexible and efficient means to handle diverse downloading scenarios.

Navigating through the complexities of internet downloads, this project emphasizes simplicity without compromising functionality. Users can easily add multiple files to the download queue, prioritize tasks based on their preferences, and, crucially, resume downloads from the point of interruption. The application's modular design ensures ease of use, extensibility, and adaptability to varying user requirements.

This introduction sets the stage for an exploration of the IDM Console Application, delving into its features, technical aspects, and considerations. As users embark on a journey of seamless and organized file downloads, this project promises a reliable and versatile tool to meet the challenges of today's dynamic digital landscape.

### Problem Statement
**Problem Statement:** Develop a console-based Internet Download Manager (IDM) using Java.

**I. Problem Explanation:**
In the digital age, efficient download management is a critical aspect of users' online experiences. The current lack of a comprehensive and user-friendly console-based solution for internet downloads poses a challenge. Users often face difficulties in managing multiple downloads simultaneously, resuming interrupted downloads, and prioritizing tasks based on their preferences. The absence of a streamlined tool that can be accessed through the console exacerbates this challenge.

The problem at hand involves the development of an Internet Download Manager (IDM) Console Application. This application should allow users to interact with the program via the console, entering essential elements such as download URLs and file names. The results should be reflected in the console, displaying progress updates and download completion messages. The program should also provide a means for users to organize and manage downloaded files effectively.

**II. Constraints:**
- **Console-Based Interaction:** The application must operate within the confines of a console-based interface, limiting the graphical or visual representation of data. This constraint emphasizes the need for a text-based and user-friendly interaction model.
- **Network Limitations:** The program's functionality heavily depends on the reliability of network connections. Users may experience interruptions or delays due to network issues, requiring the application to handle such scenarios gracefully.
- **Server Compatibility:** The application assumes that the servers hosting the files support range requests for download resumption. In cases where servers do not support this feature, the application may face limitations in resuming interrupted downloads.
- **User Input Validation:** The program must implement robust validation mechanisms to ensure that user-provided inputs, such as URLs and file names, are valid and adhere to the expected format. This is crucial for preventing errors and enhancing user experience.

### Methodology
**Algorithm/Pseudocode**
Below is the Pseudocode for The console-based Internet Download Manager (IDM)

```java
// Pseudocode for IDM
class DownloadManager:
    Queue<DownloadTask> downloadQueue
    
    method init():
        downloadQueue = new Queue<DownloadTask>()
    
    method addDownload(url, fileName):
        task = new DownloadTask(url, fileName)
        downloadQueue.enqueue(task)
        print "Download added to the queue: " + fileName
    
    method startDownloads():
        while not downloadQueue.isEmpty():
            task = downloadQueue.dequeue()
            spawn new thread for task.run()

class DownloadTask implements Runnable:
    String url
    String fileName
    
    method init(url, fileName):
        this.url = url
        this.fileName = fileName
    
    method run():
        try:
            connection = establishConnection(url)
            fileSize = connection.getContentLength()
            downloadedSize = getDownloadedSize(fileName)
            if downloadedSize > 0:
                setRangeHeader(connection, downloadedSize)
            inputStream = connection.getInputStream()
            outputStream = openFileOutputStream(fileName, downloadedSize)
            buffer = new byte[BUFFER_SIZE]
            bytesRead = 0
            while (bytesRead != -1):
                bytesRead = inputStream.read(buffer)
                outputStream.write(buffer, 0, bytesRead)
                downloadedSize += bytesRead
                printDownloadProgress(fileName, downloadedSize, fileSize)
            closeStreams(inputStream, outputStream)
            print "\nDownload complete: " + fileName
        catch (Exception e):
            print "Error while downloading " + fileName
            printStackTrace(e)
    
    method establishConnection(url):
        // Create and configure HttpURLConnection
        connection = ...
        return connection
    
    method setRangeHeader(connection, downloadedSize):
        // Set Range header for resuming downloads
        connection.setRequestProperty("Range", "bytes=" + downloadedSize + "-")
    
    method openFileOutputStream(fileName, downloadedSize):
        // Open FileOutputStream with append mode to resume downloads
        outputStream = new FileOutputStream(fileName, true)
        outputStream.seek(downloadedSize)
        return outputStream
    
    method getDownloadedSize(fileName):
        // Get the size of the already downloaded portion of the file
        return sizeOfFile(fileName)
    
    method sizeOfFile(fileName):
        // Return the size of the file
        return ...
    
    method printDownloadProgress(fileName, downloadedSize, fileSize):
        // Print download progress
        print "\rDownloading " + fileName + ": " + downloadedSize + " / " + fileSize + " bytes"
    
    method closeStreams(inputStream, outputStream):
        // Close InputStream and OutputStream
        inputStream.close()
        outputStream.close()
```

### Implementation
First of all, we have to import all the important Java classes. Now we define a DownloadManager Class which has constants:
- **BUFFER_SIZE**: Defines the size of the buffer used for reading and writing data during downloads.
- **DOWNLOADS_FOLDER**: Specifies the folder where downloaded files will be saved.

We have taken an ArrayList which helps us to download the code in a sequence. As we can see in line no 23, we added the download to the queue.

### Result & Implementation
First of all,

 compile the Java code using Javac and then execute the code. Now the code will ask us to choose one option from the 3 given options.
- When the user selects option 1 to add a download, the program prompts for a URL and a file name. The user enters the details, and the program adds the download to the queue, displaying a confirmation message.
- If the user selects option 2 to start downloads, the program initiates the download tasks concurrently using separate threads. Download progress is displayed for each task in real time as data is received from the server.
- The program establishes a connection to the specified URL, retrieves the file size, and downloads the file in chunks. Download progress is continuously displayed in the console.
- If you want to exit from the code, enter 3.

Confirmation messages are displayed when a download is added to the queue, enhancing user feedback.
- **Multiple File Downloads**: Simultaneously download various files.
  We have selected option 1 to add a download. Now when the program asks again, we have to enter 1 to add another download link to the queue.
- **Queue Management**: Prioritize downloads, allowing users to manage the order and timing of their downloads.
  The download will perform in the sequence we had provided at the time of execution.
- **File Management**: Organize downloaded files by categories and store them in specified folders.
  Once a download is complete, the program prints a completion message along with the absolute path where the file is saved. The files are saved in the specified folders.

### Conclusion
In conclusion, the console-based Internet Download Manager (IDM) developed in Java presents a fundamental yet functional solution for handling multiple file downloads. The project successfully achieves its primary objectives, allowing users to add and initiate concurrent downloads through a straightforward console interface. Leveraging the capabilities of HttpURLConnection for establishing HTTP connections, the IDM interacts effectively with servers, enabling the retrieval of files from the internet.

One of the noteworthy features of the implemented IDM is its real-time progress display, which keeps users informed about the status of each ongoing download. This feedback mechanism, combined with the simplicity of the console interface, ensures user engagement and a clear understanding of the download process. Additionally, the IDM supports download resumption through the use of append mode in the FileOutputStream, enabling users to resume interrupted downloads seamlessly.

While the current version of the IDM fulfils basic requirements, there is ample room for future enhancements. Advanced features, such as queue management functionalities (pausing, resuming, prioritizing), a transition to a graphical user interface (GUI) for improved user interaction, and more intelligent strategies for resuming interrupted downloads, could elevate the IDM to a more sophisticated and user-friendly tool. Furthermore, strengthening input validation and implementing a logging mechanism would contribute to the overall robustness and usability of the application.

In summary, this project serves as a solid foundation for an IDM, and its modular structure allows for iterative development and expansion. Future iterations can focus on incorporating additional features based on user feedback and specific requirements, ultimately leading to a more comprehensive and refined Internet Download Manager.
