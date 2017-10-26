namespace FormProject
{
    partial class SQLForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.queryResultsBox = new System.Windows.Forms.DataGridView();
            this.ID = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Extension = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.FileName = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Path = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.Event = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.DateTime = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.label5 = new System.Windows.Forms.Label();
            this.extensionBox = new System.Windows.Forms.ComboBox();
            this.queryButton = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.dbNameBox = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.queryResultsBox)).BeginInit();
            this.SuspendLayout();
            // 
            // queryResultsBox
            // 
            this.queryResultsBox.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.queryResultsBox.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.AllCells;
            this.queryResultsBox.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.queryResultsBox.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.ID,
            this.Extension,
            this.FileName,
            this.Path,
            this.Event,
            this.DateTime});
            this.queryResultsBox.Location = new System.Drawing.Point(2, 128);
            this.queryResultsBox.Name = "queryResultsBox";
            this.queryResultsBox.RowTemplate.Height = 24;
            this.queryResultsBox.Size = new System.Drawing.Size(907, 389);
            this.queryResultsBox.TabIndex = 0;
            // 
            // ID
            // 
            this.ID.HeaderText = "ID";
            this.ID.Name = "ID";
            this.ID.Width = 50;
            // 
            // Extension
            // 
            this.Extension.HeaderText = "Extension";
            this.Extension.Name = "Extension";
            this.Extension.Width = 98;
            // 
            // FileName
            // 
            this.FileName.HeaderText = "File Name";
            this.FileName.Name = "FileName";
            // 
            // Path
            // 
            this.Path.HeaderText = "PATH";
            this.Path.Name = "Path";
            this.Path.Width = 74;
            // 
            // Event
            // 
            this.Event.HeaderText = "Event";
            this.Event.Name = "Event";
            this.Event.Width = 73;
            // 
            // DateTime
            // 
            this.DateTime.FillWeight = 140F;
            this.DateTime.HeaderText = "Date/Time";
            this.DateTime.Name = "DateTime";
            this.DateTime.Width = 102;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(12, 35);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(151, 17);
            this.label5.TabIndex = 11;
            this.label5.Text = "Query Extension Filter:";
            // 
            // extensionBox
            // 
            this.extensionBox.FormattingEnabled = true;
            this.extensionBox.Items.AddRange(new object[] {
            "All Types (*.*)",
            "Word Document (*.docx)",
            "PDF (*.pdf)",
            "Text Document (*.txt)",
            "C# File (*.cs)",
            "Java File (*.java)"});
            this.extensionBox.Location = new System.Drawing.Point(12, 55);
            this.extensionBox.Name = "extensionBox";
            this.extensionBox.Size = new System.Drawing.Size(242, 24);
            this.extensionBox.TabIndex = 12;
            // 
            // queryButton
            // 
            this.queryButton.Location = new System.Drawing.Point(274, 53);
            this.queryButton.Name = "queryButton";
            this.queryButton.Size = new System.Drawing.Size(95, 27);
            this.queryButton.TabIndex = 18;
            this.queryButton.Text = "Query";
            this.queryButton.UseVisualStyleBackColor = true;
            this.queryButton.Click += new System.EventHandler(this.queryButton_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(434, 35);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(118, 17);
            this.label1.TabIndex = 19;
            this.label1.Text = "Database Name: ";
            // 
            // dbNameBox
            // 
            this.dbNameBox.AutoSize = true;
            this.dbNameBox.Location = new System.Drawing.Point(558, 35);
            this.dbNameBox.Name = "dbNameBox";
            this.dbNameBox.Size = new System.Drawing.Size(0, 17);
            this.dbNameBox.TabIndex = 20;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(437, 55);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(121, 24);
            this.button1.TabIndex = 21;
            this.button1.Text = "Clear Database";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.clearDatabaseButtonClick);
            // 
            // SQLForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(920, 529);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.dbNameBox);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.queryButton);
            this.Controls.Add(this.extensionBox);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.queryResultsBox);
            this.Name = "SQLForm";
            this.Text = "SQLForm";
            ((System.ComponentModel.ISupportInitialize)(this.queryResultsBox)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView queryResultsBox;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.ComboBox extensionBox;
        private System.Windows.Forms.Button queryButton;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label dbNameBox;
        private System.Windows.Forms.DataGridViewTextBoxColumn ID;
        private System.Windows.Forms.DataGridViewTextBoxColumn Extension;
        private System.Windows.Forms.DataGridViewTextBoxColumn FileName;
        private System.Windows.Forms.DataGridViewTextBoxColumn Path;
        private System.Windows.Forms.DataGridViewTextBoxColumn Event;
        private System.Windows.Forms.DataGridViewTextBoxColumn DateTime;
        private System.Windows.Forms.Button button1;
    }
}