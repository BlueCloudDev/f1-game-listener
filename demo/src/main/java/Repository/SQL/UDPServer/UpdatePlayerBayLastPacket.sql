UPDATE t_player_bay SET
  m_last_packet_received = datetime('now')
WHERE m_port = ?