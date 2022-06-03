SELECT
  id, 
  m_session_history_id,
  m_tyre_actual_compound,
  m_tyre_visual_compound,
  m_stint_number
FROM f12021.t_tyre_stint_history_data_2021
WHERE m_session_history_id = ? AND m_stint_number = ?