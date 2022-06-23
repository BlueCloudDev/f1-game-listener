SELECT
  f12021.t_track_coordinates_2021.id,
  f12021.t_track_coordinates_2021.m_track_id,
  f12021.t_track_coordinates_2021.m_track_type,
  f12021.t_track_coordinates_2021.m_volume_id,
  f12021.t_track_coordinates_2021.m_point_2_x,
  f12021.t_track_coordinates_2021.m_point_2_y,
  f12021.t_track_coordinates_2021.m_point_2_z,
  f12021.t_track_coordinates_2021.m_point_3_x,
  f12021.t_track_coordinates_2021.m_point_3_y,
  f12021.t_track_coordinates_2021.m_point_3_z
FROM f12021.t_track_coordinates_2021
WHERE f12021.t_track_coordinates_2021.m_track_id = ?