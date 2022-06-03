SELECT
  f12021.t_session_history_data_2021.id,
  f12021.t_session_history_data_2021.m_session_uid,
  f12021.t_session_history_data_2021.m_index,
  f12021.t_session_history_data_2021.m_session_lookup_id,
  f12021.t_session_history_data_2021.m_best_lap_time_lap_num,
  f12021.t_session_history_data_2021.m_best_sector_1_lap_num,
  f12021.t_session_history_data_2021.m_best_sector_2_lap_num,
  f12021.t_session_history_data_2021.m_best_sector_3_lap_num,
  f12021.t_lap_history_data_2021.id,
  f12021.t_lap_history_data_2021.m_lap_number,
  f12021.t_lap_history_data_2021.m_lap_time_in_ms,
  f12021.t_lap_history_data_2021.m_sector_1_time_in_ms,
  f12021.t_lap_history_data_2021.m_sector_2_time_in_ms,
  f12021.t_lap_history_data_2021.m_sector_3_time_in_ms,
  f12021.t_lap_history_data_2021.m_lap_valid_bit_flags,
  f12021.t_tyre_stint_history_data_2021.id,
  f12021.t_tyre_stint_history_data_2021.m_session_history_id,
  f12021.t_tyre_stint_history_data_2021.m_tyre_actual_compound,
  f12021.t_tyre_stint_history_data_2021.m_tyre_visual_compound,
  f12021.t_tyre_stint_history_data_2021.m_stint_number
FROM f12021.t_session_history_data_2021
JOIN f12021.t_lap_history_data_2021 ON f12021.t_lap_history_data_2021.m_session_history_id = f12021.t_session_history_data_2021.id
JOIN f12021.t_tyre_stint_history_data_2021 ON f12021.t_tyre_stint_history_data_2021.m_session_history_id = f12021.t_session_history_data_2021.id
WHERE f12021.t_session_history_data_2021.m_session_uid = ?