//Duke, Nick -- 2nd Form for DB Queries

using System;
using System.Data.SQLite;
using System.Windows.Forms;

namespace FormProject
{
    public partial class SQLForm : Form
    {
        private string database;
        private SQLiteConnection conn;
        private SQLiteCommand cmd;
        private SQLiteDataReader dr;
        public SQLForm(string database)
        {
            InitializeComponent();
            this.database = database;
            dbNameBox.Text = database+".db";
        }

        private void queryButton_Click(object sender, EventArgs e)//check if working
        {
            updateDataGrid();
        }

        private void clearDatabaseButtonClick(object sender, EventArgs e)
        {
            if (MessageBox.Show("Are you sure you want to erase all entries in the database?", "Warning", MessageBoxButtons.YesNo) == DialogResult.Yes)
            {
                conn = new SQLiteConnection("Data Source=" + database + ".db;Version=3;");
                conn.Open();
                cmd = new SQLiteCommand(conn);
                cmd.CommandText = "DELETE FROM Events";
                cmd.ExecuteNonQuery();
                updateDataGrid();
            }
        }

        private void updateDataGrid()
        {
            string ext = "";
            string result = "";
            queryResultsBox.Rows.Clear();
            conn = new SQLiteConnection("Data Source=" + database + ".db;Version=3;");
            conn.Open();
            cmd = new SQLiteCommand(conn);
            cmd.CommandText = "SELECT count(1) FROM sqlite_master WHERE type = 'table' AND name = 'Events'";
            result = cmd.ExecuteScalar().ToString();
            if (result.Equals("0"))
            {
                cmd.CommandText = "CREATE TABLE IF NOT EXISTS Events (ID integer primary key, Extension varchar(10), FileName varchar(100), PATH varchar(50), EventType varchar (50), DateTime varchar (25));";
                cmd.ExecuteNonQuery();
            }

            if (extensionBox.Text == "")
            {
                cmd.CommandText = "SELECT * FROM Events";
                dr = cmd.ExecuteReader();
                while (dr.Read())
                {
                    queryResultsBox.Rows.Add(dr["ID"], dr["Extension"], dr["FileName"], dr["PATH"], dr["EventType"], dr["DateTime"]);
                }
            }
            else
            {
                ext = configureExtension(extensionBox.Text);
                cmd.CommandText = "SELECT * FROM Events WHERE Extension = '" + ext + "'";

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
    }
}
