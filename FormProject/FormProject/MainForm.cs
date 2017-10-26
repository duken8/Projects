//Nick Duke
//File System Watcher GUI Assignment -- Main Form and Bulk of Code
using System;
using System.IO;
using System.Data.SQLite;
using System.Windows.Forms;
using System.Collections.Generic;
using System.Reflection;

namespace FormProject
{
    delegate void SetTextCallback(string text);
    public partial class MainForm : System.Windows.Forms.Form
    {
        private FileSystemWatcher fsw = null;
        private SQLiteConnection conn;
        private SQLiteCommand cmd;
        private Boolean save = false;
        private LinkedList<EventInfo> info;

        public MainForm()
        {
            InitializeComponent();
            info = new LinkedList<EventInfo>();
        }       

        #region ToolStrip and Menu Clicks
        private void aboutToolStripMenuItem_Click(object sender, EventArgs e)
        {
            string message = "Duke, Nick CSCD 371 File System Watcher GUI";
            string type = "About";
            MessageBox.Show(message, type);
        }

        private void startToolStripMenuItem_Click(object sender, EventArgs e)
        {
            string directory = textBox1.Text;
            string ext = comboBox1.Text;
            ext = configureExtension(ext);
            try
            {
                fsw = new FileSystemWatcher(directory, ext);
            }
            catch(Exception exc)
            {
               MessageBox.Show("Invalid Directory or Extension", "Error", MessageBoxButtons.OK);
            }
            
            if(fsw != null)
            {
                fsw.NotifyFilter = NotifyFilters.LastAccess | NotifyFilters.LastWrite
                | NotifyFilters.FileName | NotifyFilters.DirectoryName;

                fsw.Changed += new FileSystemEventHandler(OnChanged);
                fsw.Created += new FileSystemEventHandler(OnChanged);
                fsw.Deleted += new FileSystemEventHandler(OnChanged);
                fsw.Renamed += new RenamedEventHandler(OnRenamed);

                fsw.EnableRaisingEvents = true;
                fsw.IncludeSubdirectories = true;

                stopButton.Enabled = true;
                startButton.Enabled = false;
                toolStripStopButton.Enabled = true;
                writeToDatabaseButton.Enabled = false;
                stopToolStripMenuItem.Enabled = true;
                startToolStripMenuItem.Enabled = false;
                comboBox1.Enabled = false;
                toolStripPlayButton.Enabled = false;
            } 
        }        

        private void stopToolStripMenuItem_Click(object sender, EventArgs e)
        {
            fsw.Dispose();
            stopButton.Enabled = false;
            startButton.Enabled = true;
            writeToDatabaseButton.Enabled = true;
            toolStripStopButton.Enabled = false;
            stopToolStripMenuItem.Enabled = false;
            startToolStripMenuItem.Enabled = true;
            comboBox1.Enabled = true;
            toolStripPlayButton.Enabled = true;
        }

        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Close(null);
        }
        #endregion

        #region FileSystemWatcher Events
        private void OnRenamed(object sender, RenamedEventArgs e)
        {
            info.AddLast(new EventInfo(Path.GetExtension(e.FullPath), e.Name, e.FullPath, e.ChangeType.ToString(), DateTime.Now.ToString()));
            UpdateTextBox("File name: " + e.Name + " Path: " + e.FullPath + " Change Type: " + e.ChangeType + " Date and Time: " + DateTime.Now + "\n", e);
        }

        private void OnChanged(object sender, FileSystemEventArgs e)
        {
            info.AddLast(new EventInfo(Path.GetExtension(e.FullPath), e.Name, e.FullPath, e.ChangeType.ToString(), DateTime.Now.ToString()));
            UpdateTextBox("File name: " + e.Name + " Path: " + e.FullPath + " Change Type: " + e.ChangeType + " Date and Time: " + DateTime.Now + "\n", e);
        }
        #endregion

        #region Button Clicks
        private void clearButton_Click(object sender, EventArgs e)
        {
            outputBox.Clear();
        }
        private void queryButton_Click(object sender, EventArgs e)
        {
            string data = databaseBox.Text;
            if(data == "")
            {
                MessageBox.Show("Please enter a new or existing database name", "Error", MessageBoxButtons.OK);
            }
            else
            {
                if(!File.Exists(Path.GetDirectoryName(Assembly.GetExecutingAssembly().GetName().CodeBase.Substring(8)) + "\\" + data + ".db"))
                {
                    MessageBox.Show("Invalid database name", "Error", MessageBoxButtons.OK);               
                }
                else
                {
                    SQLForm form2 = new SQLForm(data);
                    form2.ShowDialog();
                }
            }    
        }

        private void WriteToDatabase_Click(object sender, EventArgs e)
        {
            WriteToDatabase();
            save = false;
        }
        #endregion

        #region Helper Methods
        private void MainForm_Closing(object sender, FormClosingEventArgs e)
        {
            Close(e);
        }

        private void Close(FormClosingEventArgs e)
        {
            if (outputBox.Text != "" && save == true)
            {
                if (MessageBox.Show("Do you want to save the current events to a database?", "Confirmation", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    if (databaseBox.Text == "")
                    {
                        MessageBox.Show("Please Enter a database name", "Error");
                        if(e != null)
                        {
                            e.Cancel = true;
                        }                
                    }
                    else
                    {
                        WriteToDatabase();
                    }
                }
            }
        }

        private void SetText(string text)
        {
            this.outputBox.AppendText(text);
        }

        private void UpdateTextBox(string text, FileSystemEventArgs e)
        {
            if (InvokeRequired)
            {
                SetTextCallback d = new SetTextCallback(SetText);
                this.Invoke(d, new object[] { text });
            }
            else
            {
                outputBox.AppendText(text);
            }
        }

        private void needsSave(object sender, EventArgs e)
        {
            save = true;
        }

        private void WriteToDatabase()
        {
            if (save == true)
            {
                string data = databaseBox.Text;
                if (databaseBox.Text != "")
                {
                    if (!File.Exists(data + ".db"))
                    {
                        SQLiteConnection.CreateFile(data+".db");
                    }

                    conn = new SQLiteConnection("Data Source=" + data + ".db" + ";Version=3;");
                    conn.Open();
                    cmd = conn.CreateCommand();
                    cmd.CommandText = "CREATE table IF NOT EXISTS Events (ID integer primary key, Extension varchar(10), FileName varchar(100), PATH varchar(50), EventType varchar (50), DateTime varchar (25));";
                    cmd.ExecuteNonQuery();
                    for (LinkedListNode<EventInfo> node = info.First; node != null; node = node.Next)
                    {
                        cmd.CommandText = "INSERT INTO Events (Extension, FileName, PATH, EventType, DateTime) VALUES ('" + node.Value.Extension + "', '" + node.Value.FileName + "', '" + node.Value.Path + "', '" + node.Value.EventType + "', '" + node.Value.DateTime + "')";
                        cmd.ExecuteNonQuery();
                    }
                }
                else
                {
                    MessageBox.Show("Please Enter a database name", "Error");
                }
            }
        }

        private string configureExtension(string ext)
        {
            switch (ext)
            {
                case "":
                    ext = "*.*";
                    break;
                case "All Types (*.*)":
                    ext = "*.*";
                    break;
                case "Word Document (*.docx)":
                    ext = "*.docx";
                    break;
                case "PDF (*.pdf)":
                    ext = "*.pdf";
                    break;
                case "Text Document (*.txt)":
                    ext = "*.txt";
                    break;
                case "C# File (*.cs)":
                    ext = "*.cs";
                    break;
                case "Java File (*.java)":
                    ext = "*.java";
                    break;
                default:
                    ext = "*" + ext;
                    break;
            }
            return ext;
        }
        #endregion
    }
}