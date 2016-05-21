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
		<td><sup>f+n</sup>/<sub>n-f</sup></td>
		<td><sup>2nf</sup>/<sub>n-f</sup></td>
	</tr>
	<tr>
		<td>0</td>
		<td>0</td>
		<td>-1</td>
		<td>0</td>
	</tr>
 </table>
 
where

- fovy = cot(angle/2) = the cotangent of the field of view angle over 2
- n is the distance to the near clipping plane
- f is the distance to the far clipping plane
- aspect is the screen aspect ratio

The problem with this transformation matrix is that as we approach the far clipping plane, the transformed value <sup>z</sup>/<sub>w</sub> approaches 1--and in the IEEE 754 representation of values, values approaching 1 have a high degree of error for small values 1 - error.

However, if we use a different transformation matrix:

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
		<td>-n</td>
		<td>0</td>
	</tr>
	<tr>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>-1</td>
	</tr>
 </table>

Notice that we've dropped the 'f' (distance from the far clipping plane) term entirely. This matrix has the property that as we approach distance -> infinity, the transformed value <sup>z</sup>/<sub>w</sub> approaches 0, and in IEEE 754 representation of values, approaching zero maintains a high degree of resolution in the bitwise representation. This allows us, in effect, to represent scenes where small nearby objects are rendered correctly in front of large, far away objects without having to resort to painting algorithm tricks.

In the extremis, this also allows us to represent objects at infinity, such as the locations of a field of stars on the sphere where the (pre-transformed) coordinate has w = 0.

Of course our clipper still has to clip against the far clipping plane; it's just mathematically in object space our far clipping plane is at infinity.

## What is this?

A fuller discussion can be found at the blog post above. This is the source kit for that discussion, demonstrating the rendering of a nearby and far away object in the same scene, rendered with the traditional transformation matrix, and with the modified transformation matrix shown above.

## License

The software contained herein is public domain. Please feel free to use the software with or without attribution in any way you see fit. I explicitly do not retain the copyright on the work and I explicitly disavow any intellectual property rights I have on the attached software.

All I ask, as a favor, is that if you publish this trick, at least give me a shout-out.
