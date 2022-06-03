SELECT
  id, 
  m_session_history_id,
  m_lap_time_in_ms,
  m_sector_1_time_in_ms,
  m_sector_2_time_in_ms,
  m_sector_3_time_in_ms,
  m_lap_valid_bit_flags,
  m_lap_number
FROM f12021.t_lap_history_data_2021
WHERE m_session_history_id = ? AND m_lap_number = ?