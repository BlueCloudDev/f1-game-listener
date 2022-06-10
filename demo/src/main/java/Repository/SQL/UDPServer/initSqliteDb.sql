CREATE TABLE IF NOT EXISTS t_player_bay (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  m_port INTEGER NOT NULL,
  m_player_name TEXT,
  m_last_packet_received DATETIME,
  m_event_id INTEGER,
  UNIQUE(m_port)
);