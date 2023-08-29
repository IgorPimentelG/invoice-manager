CREATE TABLE IF NOT EXISTS email_log (
    id CHAR(36) PRIMARY KEY,
    owner_ref CHAR(36) NOT NULL,
    from VARCHAR(100) NOT NULL,
    to VARCHAR(100) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    status ENUM('SENT', 'ERROR') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "owner_ref")
	private String ownerRef;

	private String from;
	private String to;
	private String subject;
	private String content;
	private Status status;

	@Column(name = "created_at")
	private Date createdAt;