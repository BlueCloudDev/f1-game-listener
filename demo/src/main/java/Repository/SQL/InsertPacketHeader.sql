INSERT INTO t_packet_header (
  m_packet_format,  
  m_player_name,
  m_game_major_version,
  m_game_minor_version,
  m_packet_version,
  m_packet_id,
  m_session_UID,
  m_session_time,
  m_frame_identifier,
  m_player_car_index,
  m_secondary_player_car_index,
  m_created_on
) VALUES (
  ?,?,?,?,?,?,?,?,?,?,
  ?,?
)