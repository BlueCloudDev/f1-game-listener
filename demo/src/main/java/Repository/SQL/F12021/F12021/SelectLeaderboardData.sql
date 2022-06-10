SELECT
  f12021.t_session_lookup_2021.m_player_name,
  MAX(f12021.t_session_lookup_2021.m_created_on),
  f12021.t_session_data_2021.m_track_id,
  f12021.t_lap_history_data_2021.m_lap_number,
  f12021.t_lap_history_data_2021.m_lap_time_in_ms,
  f12021.t_lap_history_data_2021.m_sector_1_time_in_ms,
  f12021.t_lap_history_data_2021.m_sector_2_time_in_ms,
  f12021.t_lap_history_data_2021.m_sector_3_time_in_ms,
  f12021.t_lap_history_data_2021.m_lap_valid_bit_flags,
  f12021.t_session_lookup_2021.m_event_id
FROM f12021.t_session_lookup_2021
JOIN f12021.t_session_history_data_2021 ON f12021.t_session_history_data_2021.m_session_uid = f12021.t_session_lookup_2021.m_session_uid
JOIN f12021.t_lap_history_data_2021 ON f12021.t_lap_history_data_2021.m_session_history_id = f12021.t_session_history_data_2021.id
JOIN f12021.t_session_data_2021 ON f12021.t_session_data_2021.m_session_lookup_id = f12021.t_session_lookup_2021.id
WHERE f12021.t_session_history_data_2021.m_index = f12021.t_session_lookup_2021.m_player_car_index 
AND f12021.t_lap_history_data_2021.m_lap_time_in_ms > 0
AND f12021.t_session_lookup_2021.m_event_id = ?
GROUP BY 
  f12021.t_session_lookup_2021.m_player_name,
  f12021.t_session_data_2021.m_track_id,
  f12021.t_lap_history_data_2021.m_lap_number,
  f12021.t_lap_history_data_2021.m_lap_time_in_ms,
  f12021.t_lap_history_data_2021.m_sector_1_time_in_ms,
  f12021.t_lap_history_data_2021.m_sector_2_time_in_ms,
  f12021.t_lap_history_data_2021.m_sector_3_time_in_ms,
  f12021.t_lap_history_data_2021.m_lap_valid_bit_flags,
  f12021.t_session_lookup_2021.m_event_id
ORDER BY f12021.t_lap_history_data_2021.m_lap_time_in_ms ASC