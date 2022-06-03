SELECT
  id,
  m_session_UID,
  m_index,
  m_session_lookup_id,
  m_best_lap_time_lap_num,
  m_best_sector_1_lap_num,
  m_best_sector_2_lap_num,
  m_best_sector_3_lap_num
FROM f12021.t_session_history_data_2021
WHERE m_session_UID = ?