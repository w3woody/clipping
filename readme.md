# Clipping

Render test application behind the paper ["Goodbye Far Clipping Plane"](http://chaosinmotion.com/blog/?p=555).

## Abstract

The normal perspective transformation matrix used in computer graphics to represent the transformation from object space to screen perspective space is typically represented by the following matrix:

<table>
	<tr>
		<td rowspan="4"><b>P =</b></td>
		<td><sup>fov</sup>/<sub>aspect</sub></td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
	</tr>
	<tr>
		<td>0</td>
		<td>fov</td>
		<td>0</td>
		<td>0</td>
	</tr>
	<tr>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
	</tr>
	<tr>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
	</tr>
 </table>
 